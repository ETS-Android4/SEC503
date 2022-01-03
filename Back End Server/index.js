var crypto = require('crypto');
var uuid = require('uuid');
var express = require('express');
var bodyParser = require('body-parser');
var mysql = require('mysql');


const { connected, resourceUsage } = require('process');
const { SIGBREAK, POINT_CONVERSION_HYBRID } = require('constants');
const { read } = require('fs');
// const { mkdirSync } = require('fs');


const nodemailer = require('nodemailer');

let transporter = nodemailer.createTransport({
    host:'smtp.gmail.com',
    port:587,
    auth:{
        user:"asd566717@gmail.com",
        pass:"Samah7922"
    }
})

function EmailVerification(email){
    var token = genRandomString(6);
    message = {
        from: "Test@email.com",
        to: email,
        subject: "OTP",
        text: token
   }
   transporter.sendMail(message, function(err, info) {
        if (err) {
          console.log(err)
        } else {
          console.log(info);
   }})
   return token
};


var con = mysql.createConnection({
    host:'127.0.0.1',
    user: 'root',
    password: 'root',
    database: 'Project'
});


var app=express();
app.use(bodyParser.json()) /// Accept JSON Params
app.use(bodyParser.urlencoded({extended: true})); // Accept URL Encoded params


// Hashing the password

var genRandomString = function(length){
    return crypto.randomBytes(Math.ceil(length/2))
        .toString('hex')
        .slice(0,length);
};

var sha512 = function(password,salt){
    var hash = crypto.createHmac('sha512',salt);
    hash.update(password);
    var value = hash.digest('hex');
    return{
        salt:salt,
        passwordHash:value
    }
};

function SaltHashPassword(UserPassword){
    var salt = genRandomString(16);
    var passwordData = sha512(UserPassword,salt);
    return passwordData;
};


function checkHashPassword(password,salt){
    var pwdh = sha512(password, salt);
    return pwdh;
}


app.post('/register/',(req,res,next)=>{
    var post_data = req.body;
    var uid = uuid.v4();
    console.log(uid);
    var plain_text_password = post_data.password;
    var Hashed_Password = SaltHashPassword(plain_text_password);
    console.log(plain_text_password);
    var salt = Hashed_Password.salt;
    console.log(Hashed_Password.passwordHash);
    var name = post_data.name;
    console.log(name);

    var email = post_data.email;
    console.log(email);

    con.query("SELECT * FROM Project.`user` where email=?", [email],function(err,result,fields){
        con.on('error',function(err){
            console.log('[MySQL ERROR]',err);
        });

        if(result && result.length)
        res.json('User already exists !!!');
        else
        {
            con.query('INSERT INTO Project.`user` (unique_id, name, email, encrypted_password, salt, created_at, updated_at) VALUES(?, ?, ?, ?, ?, NOW(), NOW());',[uid,name,email,Hashed_Password.passwordHash,salt],function(err,result,fields){
                con.on('error',function(err){
                    console.log('[MySQL ERROR]',err);
                    res.json('Regstration error: ',err)
                });
                res.json('Registration Done successfully')
            })
        }
    });
    
});

app.post('/login/', (req,res,next)=>{

    var post_data = req.body;

    var user_password = post_data.password;
    var email = post_data.email;

    con.query("SELECT * FROM Project.`user` where email=?", [email],function(err,result,fields){
        con.on('error',function(err){
            console.log('[MySQL ERROR]',err);
        });

        if(result && result.length)
        {
            var salt = result[0].salt;
            // console.log(salt);
            var encrypted_password = result[0].encrypted_password;
            // console.log(user_password);
            var Hashed_Password = checkHashPassword(user_password,salt).passwordHash;
            // console.log(Hashed_Password);
            if(encrypted_password == Hashed_Password)
            {
                
                var token = EmailVerification(result[0].email);
                var session_id = genRandomString(100);
                con.query("INSERT INTO Project.otp (id, session_id, otp,create_at) VALUES(?, ?, ?,NOW());",[result[0].id,session_id,token],function(err,result,fields){
                    con.on('error',function(err){
                        console.log('[MySQL ERROR]',err);
                    });
                });
                res.json(session_id);
                // console.log(token)

            }
            else
            {
                res.end(JSON.stringify("Login Failed !!"));
            }

        }
        else
        {
            res.json("User Does Not Exists !!");
        }
    });
})

app.post('/logout/',(req,res,next)=>{

    var post_data = req.body;

    var session_id = post_data.session_id;

    con.query("SELECT * FROM Project.otp where session_id=?;", [session_id],function(err,result,fields){
        con.on('error',function(err){
            console.log('[MySQL ERROR]',err);
        });
        if(result && result.length)
        {
            con.query("DELETE FROM Project.authenticated_users WHERE session_id=?;", [session_id],function(err,result,fields){
                con.on('error',function(err){
                    console.log('[MySQL ERROR]',err);
                });
                res.json("Logout Seccussfully")
            
            });
        }
        
    })



})

app.post('/otp/',(req,res,next)=>{
    var post_data = req.body;
    var otp = post_data.otp;
    console.log(otp)
    var session_id_b = post_data.session_id;
    var session_id = session_id_b.replace(/['"]+/g, '');
    console.log(session_id)
    con.query("SELECT * FROM Project.otp where session_id=?", [session_id],function(err,result,fields){
        con.on('error',function(err){
            console.log('[MySQL ERROR]',err);
        });
        if(result && result.length)
        {
            if(otp == result[0].otp){
                // login successfully
                con.query("UPDATE Porject.otp SET state = '1' WHERE otp=?", [otp],function(err,result,fields){
                    con.on('error',function(err){
                        console.log('[MySQL ERROR]',err);
                    });
                
                });

                con.query("INSERT INTO Project.authenticated_users (id, session_id) VALUES(?, ?);", [result[0].id,result[0].session_id],function(err,result,fields){
                    con.on('error',function(err){
                        console.log('[MySQL ERROR]',err);
                    });
                
                });
                res.json(result[0].id);



            }
        }else{
            res.json("Failed");

        }
        
    })

     
})


// Key Management

app.post('/KeyManagemnt/',(req,res,next)=>{
    var post_data = req.body;

    var option = post_data.option;
    // var option = option_b.replace(/['"]+/g, '');
    console.log(option);
    var Pubkey = post_data.Pubkey;
    // var Pubkey = Pubkey_b.replace(/['"]+/g, '');
    console.log(Pubkey);
    var session_id_b = post_data.session_id;
    var session_id = session_id_b.replace(/['"]+/g, '');
    var peer_id = post_data.peer_id;


    if(option == 'push'){
        con.query("SELECT * FROM Project.authenticated_users where session_id=?;", [session_id],function(err,result,fields){
            con.on('error',function(err){
                console.log('[MySQL ERROR]',err);
            });

            if(result && result.length){
                con.query("INSERT INTO Project.KeyManagment (id, session_id, Pubkey) VALUES(?, ?, ?);", [result[0].id,session_id,Pubkey],function(err,result,fields){
                    con.on('error',function(err){
                        console.log('[MySQL ERROR]',err);
                    });
                    
                })
            
            }
            res.json("public key is uploaded !!")
        })
    }


    if(option == 'ask'){
        console.log(peer_id);
        con.query("SELECT * FROM Project.KeyManagment where id=?;", [peer_id],function(err,result,fields){
            con.on('error',function(err){
                console.log('[MySQL ERROR]',err);
            });
            console.log(result[0].Pubkey)
            res.json(result[0].Pubkey)
            // res.json("public key is uploaded !!")
        })
    }
})

app.post('/ShareKey/',(req,res,next)=>{
    var post_data = req.body;

    var encrypted_key = post_data.encrypted_key.replace(/['"]+/g, '')
    console.log(encrypted_key);
    var chatting_id = post_data.chatting_id
    console.log(chatting_id);



    con.query("INSERT INTO Project.KeyExchange (chatting_id, secret_key) VALUES(?, ?);", [chatting_id,encrypted_key],function(err,result,fields){
        con.on('error',function(err){
            console.log('[MySQL ERROR]',err);
        });
        res.json("secret ket has been sent");
    })



});

app.post('/AskSecKey/',(req,res,next)=>{
	var post_data = req.body;
	
	var request_id = post_data.request_id;
    console.log(request_id);
	
	con.query("SELECT secret_key FROM Project.KeyExchange WHERE chatting_id=?",[request_id],function(err,result,fields){
		console.log('[MySQL ERROR]',err);
        console.log(result[0].secret_key.replace("\n",""));
        var data = result[0].secret_key.replace("\"","");
        res.json(data);
	});
	
});



/// Chat Managment Module

app.post('/ChattManagement/',(req,res,next)=>{

    var post_data = req.body;

    //request_id
    var request_id = post_data.request_id;
    console.log(request_id);
    //requester_id
    var requester_session_id = post_data.requester_id
    console.log(requester_session_id);
    //requester_message
    var email = post_data.email;
    console.log(email);
    //encrypted_key
    var chatting_id = post_data.chatting_id;
    //responded_status
    var responded_status = post_data.responded_status;
    console.log(responded_status);
    //option
    var option = post_data.option;
    console.log(option);

    if(option == 'req'){

        con.query("INSERT INTO Project.ChatRequest (requester_id, rquested_email, status, chatting_id, request_id) VALUES(?, ?, ?, NULL, ?);", [requester_session_id,email,responded_status,request_id],function(err,result,fields){
            con.on('error',function(err){
                console.log('[MySQL ERROR]',err);
            });
            res.json("request has been sent")
        })

    }
    if (option == 'chk_res'){
        con.query("SELECT requester_id,request_id, status FROM Project.ChatRequest WHERE rquested_email=? AND status=?",[email,responded_status],function(err,result,fields){
            if(result && result.length){
                res.json(result);
            }
            else{
                res.json("no requests");
            }
        } )
    }

/*     if(option == 'res'){
        con.query('UPDATE Project.ChatRequest SET status=?, chatting_id=? WHERE request_id=?',[responded_status,chatting_id,request_id],function(err,result,fields){
            con.on('error',function(err){
                console.log('[MySQL ERROR]',err);
            });
        });
    } */
    

    

})


// Start Server

app.setMaxListeners(0);

app.listen(3000,()=>{
    console.log('NodeJs Server is running on port 3000')
})