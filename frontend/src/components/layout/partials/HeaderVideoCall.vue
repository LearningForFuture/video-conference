<template>
    <div class="container-video-call p-0 m-0">
        <div class="row-video-call mx-auto">
            <div class="wrap-devices">
                <div class="btn-camera">
                    <i class="ti-video-camera text-white"></i>
                </div>
                <div class="btn-microphone">
                    <i class="ti-microphone text-white"></i>
                </div>
                <div class="btn-chat">
                    <i class="ti-comments text-white"></i>
                </div>
                <div class="btn-paticipant">
                    <button>Participants</button>
                </div>
                <div class="share-screen">
                    <i calss="ti-share-alt text-white"></i>
                </div>
            </div>
            <div class="btn-leave-meeting">
                <button>Leave</button>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'Headervideocall',

    data() {
        return {
            
        };
    },

    mounted() {
        
    },

    methods: {
        
    },
};
</script>

<style lang="css" scoped>
.container-video-call {
    background-color: rgb(58, 55, 55);
    filter: opacity(85%);
    width: 100%;
}

.row-video-call {
    display: flex;
    flex-flow: row nowrap;
    justify-content: flex-end;
    align-items: center;
    padding: 10px 15px;
}

.wrap-devices {
    display: flex;
    flex-flow: row nowrap;
    justify-content: center;
    align-items: center;
}

.btn-microphone,
.btn-chat,
.btn-camera {
    margin-right: 15px;
    cursor: pointer;
}

.btn-chat i,
.btn-microphone i,
.btn-camera i{
    font-size: 1.2rem;
}

.btn-paticipant button {
    outline: none;
    padding: 4px 15px;
    border: 1px solid rgba(43, 13, 13, 0.842);
    color: #FFF;
    font-weight: 600;
    background-color:rgba(43, 13, 13, 0.842);
    border-radius: 5px;
    box-shadow: 1px 2px 2px rgb(43, 13, 13);
    margin-right: 15px
}

.btn-leave-meeting button {
    outline: none;
    padding: 4px 15px;
    border: 1px solid rgb(216, 67, 67);
    color: #FFF;
    font-weight: 600;
    background-color:rgb(216, 67, 67);
    border-radius: 5px;
    box-shadow: 1px 3px 2px rgb(216, 33, 33);
}
</style>