
const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();



exports.ondelete = functions.database.ref("Class").onDelete((snapshot,context) => {
    admin.database().ref("hello_node").remove().catch();
});

exports.onGroupAdded=functions.database.ref("functionData").onUpdate((snapshot,context)=>{

    admin.database().ref("hello_new_node").set("changed");
    return snapshot.before.val()
});


exports.onGroupAddeds=functions.database.ref("Chat_Users/{uid}/username")
    .onUpdate((snapshot,context)=>{
        let updatedname = snapshot.after.val();
        var ref=admin.database().ref('Chat_Users/'+context.params.uid+'/sent_requests');
        ref.on('value',function(data){
            let users=data.val();
            let keys=Object.keys(users);
            for(let i=0;i<keys.length;i++)
            {
                var k=keys[i];
                var username=users[k].username;
                var uid=users[k].uid;
                admin.database().ref('Chat_Users/'+uid+'/friend_requests/'+context.params.uid+'/username').set(updatedname);
            }

        },function(error){
            admin.database().ref("hello_new_node").set("error"+error);
        });
        return snapshot.before.val()
});




