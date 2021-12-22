<template>
  <div class="wrapper-teams">
    <div class="container-teams">
      <teams-list-groups :is-joined="isJoined" />
      <div v-show="isJoined">
        <a
          class="text-primary"
          @click="backLayoutTeams"
        ><span>&gt;&nbsp;Back</span></a>
        <div class="row-team h-auto row-join-group">
          <div class="team-grid">
            <div class="group-wrapper">
              <a
                class=""
                href="javascript:void(0)"
              >
                <div class="wrapper-avatar-group mw-30">
                  <img
                    class="img-responsive group-avatar mw-100"
                    src="https://www.pngfind.com/pngs/m/343-3436560_our-team-european-joint-programme-on-rare-diseases.png"
                    alt="group-avatar"
                  >
                </div>
                <div class="group-name">
                  <h5 class="text-center">Create new group</h5>
                </div>
                <button
                  class="btn btn-secondary"
                  @click="$refs.modalCreateGroup.openModal()"
                ><i class="fa fa-users mr-1 text-white" />Create group</button>
              </a>
            </div>
          </div>
          <div class="team-grid">
            <div class="group-wrapper">
              <a
                class=""
                href="javascript:void(0)"
              >
                <div class="wrapper-avatar-group">
                  <img
                    class="img-responsive group-avatar mw-100"
                    src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRcnSCYFR1gIyd8UK1Kg3objNNdZRpx_eVkWg&usqp=CAU"
                    alt="group-avatar"
                  >
                </div>
                <div class="group-name">
                  <h5 class="text-center">Join group</h5>
                </div>
                <form
                  action
                  method="post"
                  @submit.prevent="handleSubmitJoinRoom"
                >
                  <div class="form-group">
                    <input
                      v-model.trim="code"
                      type="text"
                      name="code"
                      class="form-control"
                      placeholder="Enter code"
                      required
                    >
                    <small
                      v-if="!isFieldCode"
                      class="text-danger"
                    >Code required</small>
                  </div>
                  <input
                    type="submit"
                    class="btn-join"
                    value="Join"
                  >
                </form>
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>

    <modal-form
      ref="modalCreateGroup"
      @handle-submit="handleSubmit()"
    >
      <template v-slot:header>
        <h5>Create new group</h5>
      </template>
      <template v-slot:body>
        <div class="form-group">
          <label for="roomName">Tên nhóm</label>
          <input
            id="roomName"
            v-model.trim="nameGroup"
            type="text"
            name="roomName"
            class="form-control"
            aria-describedby="nameGroup"
            placeholder="Enter name group"
          >
          <span
            v-if="errors['nameGroup'] && !errors['nameGroup'].checked"
            class="text-danger"
          >{{ errors['nameGroup'].message }}</span>
        </div>
        <div class="input-group">
          <select
            v-model="isPublic"
            class="custom-select"
            name="isPublic"
          >
            <option value="true">
              Công khai - Mọi người trong tổ chức bạn đều có thể tham gia
            </option>
            <option value="false">
              Riêng tư - Chỉ chủ sở hữu nhóm mới có thể thêm thành viên
            </option>
          </select>
        </div>
      </template>
      <template v-slot:footer>
        <div>
          <button
            class="btn btn-danger"
            @click="$refs.modalCreateGroup.closeModal()"
          >
            Cancel
          </button>
          <input
            type="submit"
            class="btn btn-success float-right"
            value="Save"
            @click="handleSubmit()"
          >
        </div>
      </template>
    </modal-form>
  </div>
</template>

<script>
import {
  mapState,
  mapActions,
  mapGetters
} from 'vuex'
import TeamsListGroups from './TeamsListGroups.vue'
import ModalForm from '../../modal/ModalForm.vue';

export default {
  name: 'TeamsBody',
  components: {
    TeamsListGroups,
    ModalForm
  },
  data() { 
    return { 
      nameGroup: null, isPublic: false, errors: {}, isFieldCode: false,
      code: null  
    } 
  },

  computed: {
    ...mapState({
      isJoined: state => state.rooms.isJoined
    }),

    ...mapGetters('users', ['getUserId']),
  },

  watch: {
    nameGroup(value) {
      this.nameGroup = value;
      this.validNameGroup();
    },

    code(value) {
      this.code = value;
      this.validCode();
    }
  },

  methods: {
    ...mapActions('rooms', ['changeStateJoined', 'addNewRoom', 'joinRoomByCode']),

    backLayoutTeams() {
      this.changeStateJoined(false);
    },

    validNameGroup() {
      if (!this.nameGroup) {
        this.errors['nameGroup'] = {
          checked: false,
          message: 'Name group is required'
        }
      } else {
        this.errors['nameGroup'] = { checked: true, mesage:'' }
      }
    },

    validCode() {
      if (!this.code) {
        this.isFieldCode = false;
      } else this.isFieldCode = true;
    },

    async handleSubmit() {
      try {
        this.validNameGroup();
        if (this.errors['nameGroup'].checked) {
          // do something
          await this.addNewRoom({
            roomName: this.nameGroup,
            isPublic: this.isPublic,
            createdBy: this.getUserId
          });
          this.$refs.modalCreateGroup.closeModal();
          this.backLayoutTeams();
        }
      } catch(error) {
        console.log(error.response);
      }
    },

    async handleSubmitJoinRoom() {
      try {
        this.validCode();
        if (this.isFieldCode) {
          await this.joinRoomByCode({
            roomCode: this.code
          })
        }
        this.backLayoutTeams();
      } catch (err) {
        console.log(err.response)
        if (err.response.status === 400) {
          err.response.data.errors.forEach(error => {
            this.$toast.error(error.message, {
              position: "top-right",
              timeout: 5000,
              closeOnClick: true,
              pauseOnFocusLoss: true,
              pauseOnHover: true,
              draggable: true,
              draggablePercent: 0.6,
              showCloseButtonOnHover: false,
              hideProgressBar: true,
              closeButton: "button",
              icon: true,
              rtl: false
            });
          })
        }
      }
    }

  }
}
</script>

<style scoped>
.row-join-group .group-wrapper .wrapper-avatar-group {
    max-width: 30% !important;
}

.row-join-group .group-wrapper .group-name h5 {
    font-size: 20px
}

.btn-join {
    outline: none;
    font-size: 16px;
    padding: 4px 8px;
    border: 1px solid rgb(106, 90, 205);
    background-color: rgb(106, 90, 205);
    border-radius: 2px;
    color: #fff;
    float: right
}

.group-wrapper a {
    display: flex;
    flex-flow: column wrap;
    justify-content: center;
    align-items: center
}

a {
    cursor: pointer;
}

.overflow-hidden {
    overflow: hidden;
}

.custom-select {
  font-size: 14px;
  background: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='14.001' height='8.165' viewBox='0 0 14.001 8.165'%3E%3Cdefs%3E%3Cstyle%3E.a%7Bfill:%23212121;%7D%3C/style%3E%3C/defs%3E%3Cpath class='a' d='M13.861,60.224l-.7-.7a.441.441,0,0,0-.645,0L7,65.036,1.487,59.522a.441.441,0,0,0-.645,0l-.7.7a.441.441,0,0,0,0,.645l6.537,6.538a.441.441,0,0,0,.645,0l6.538-6.538a.442.442,0,0,0,0-.645Z' transform='translate(0 -59.382)'/%3E%3C/svg%3E") no-repeat right .75rem center/8px 10px;

}
</style>
