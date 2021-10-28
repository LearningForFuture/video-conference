export default {
    URL_SOCKET: 'ws://localhost/groups/chats/:user_id',
    STATUS_OPTIONS : {
        available: 'available',
        in_a_call: 'in-a-call',
        busy: 'busy',
        appear_away: 'appear-away'
    },
    ICE_SERVERs: [
        { urls: "stun:stun.l.google.com:19302" },
        { urls: "stun1.l.google.com:19302" },
        { urls: "stun2.l.google.com:19302" },
        { urls: "stun3.l.google.com:19302" },
        { urls: "stun4.l.google.com:19302" },
        { urls: "stun:stun.stunprotocol.org:3478" },
        { urls: "stun:stun.sipnet.net:3478" },
        { urls: "stun:stun.ideasip.com:3478" },
        { urls: "stun:stun.iptel.org:3478" },
        { urls: "stun01.sipphone.com" },
        { urls: "turn:numb.viagenie.ca", username: "imvasanthv@gmail.com", credential: "d0ntuseme" },
        {
            urls: [
                "turn:173.194.72.127:19305?transport=udp",
                "turn:[2404:6800:4008:C01::7F]:19305?transport=udp",
                "turn:173.194.72.127:443?transport=tcp",
                "turn:[2404:6800:4008:C01::7F]:443?transport=tcp",
            ],
            username: "CKjCuLwFEgahxNRjuTAYzc/s6OMT",
            credential: "u1SQDR/SQsPQIxXNWQT7czc/G4c=",
        },
    ]
}