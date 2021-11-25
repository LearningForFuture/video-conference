<template>
  <div id="meetings">
    <perfect-scrollbar>
      <div
        v-for="(meeting, meetingId) in getAllMeetings"
        :key="meetingId"
      >
        <meeting-items>
          <template v-slot:time>
            <div class="col-12 text-center" v-text="formatDate(meeting.startedAt)">
              <!-- October 5, 2021 -->
            </div>
          </template>
          <template v-slot:header>
            <div class="title-meeting">
              {{ titleStart }}
            </div>
          </template>
          <template v-slot:content>
            <div
              v-if="!meeting.finishedAt"
              class="bg-wrap-join px-3 py-1"
            >
              <div class="join-meeting">
                <div class="title-meeting mb-3">
                  {{ titleStart }}
                </div>
                <router-link 
                  tag="a" 
                  class="btn btn-join" 
                  :to="{ path: `/conversations/teams/room/${meeting.roomId}/meeting/${meeting.meetingId}` }"
                >
                  Join
                </router-link>
              </div>
              <div class="time-meeting">
                <p
                  class="time"
                  v-text="meeting.time"
                />
              </div>
            </div>
            <div class="ml-3 py-2">
              <a
                class="text-info"
                href="javascript:void(0)"
              >{{ content }}</a>
            </div>
          </template>
        </meeting-items>
        <meeting-items v-if="meeting.finishedAt != null">
          <template v-slot:time>
            <div class="col-12 text-center" v-text="formatDate(meeting.finishedAt)">
              <!-- October 5, 2021 -->
            </div>
          </template>
          <template v-slot:header>
            <div class="title-meeting">
              {{ titleEnd }}
            </div>
          </template>
          <template v-slot:content>
            <div class="ml-3 py-2">
              <a
                class="text-info"
                href="javascript:void(0)"
              >{{ content }}</a>
            </div>
          </template>
        </meeting-items>
      </div>
    </perfect-scrollbar>
  </div>
</template>

<script>
import MeetingItems from './MeetingItems.vue';
import { mapActions, mapGetters} from 'vuex';
import { PerfectScrollbar } from "vue2-perfect-scrollbar";
import 'vue2-perfect-scrollbar/dist/vue2-perfect-scrollbar.css'

export default {
  name: 'Roombody',
  components: { MeetingItems, PerfectScrollbar},

  props: ['roomId'],

  data() {
    return {
      titleStart: 'Meeting in "General" statrted',
      titleEnd: 'Meeting in "General" ended',
      content: '12 replies',
      times: {},
    };
  },

  computed: {
    ...mapGetters('meeting', ['getAllMeetings']),
  },

  created() {
    setInterval(() => this.updateTime(), 1000);
  },

  mounted() {
    const maxHeight = document.querySelector('nav.navigation').offsetHeight -
      document.querySelector(".header").offsetHeight;

    document.getElementById("meetings").style.maxHeight = maxHeight + 'px';
    document.querySelector(".ps").style.height = maxHeight + "px";      
    this.getAllMeetingRoomCurrent();
  },

  methods: {
    ...mapActions('meeting', ['getMeetingByRoomId', 'setTimeMeeting']),

    updateTime() {
      this.getAllMeetings.forEach(async meeting => { 
        if (!meeting.finishedAt) { 
          const cd = new Date(new Date() - new Date(meeting.startedAt));
          const hours = cd.getUTCHours() === 0 ? '': this.zeroPadding(cd.getUTCHours() + 1, 2) + ':'; 
          this.setTimeMeeting({
            meetingId: meeting.meetingId,
            time: hours +
              this.zeroPadding(cd.getMinutes(), 2) + ':' + this.zeroPadding(cd.getSeconds(), 2)
          })
        } 
      })
    },

    zeroPadding(num, digit) {
      var zero = '';
      for (var i = 0; i < digit; i++) {
        zero += '0';
      }
      return (zero + num).slice(-digit);
    },

    formatDate(date) {
      const month = [
        'January', 'February', 'March', 'April', 'May', 'June', 'July', 'Agust', 
        'September', 'October', 'November', 'December'];
      const time = new Date(date);
      return this.zeroPadding(month[this.zeroPadding(time.getMonth(), 2)]) + ' ' +
        this.zeroPadding(time.getDate(), 2) + ", " + this.zeroPadding(time.getFullYear());
    },

    async getAllMeetingRoomCurrent() {
      try {
        await this.getMeetingByRoomId(parseInt(this.roomId));
      } catch (e) {
        this.$log.debug(e.response)
      }
    }

  },
};
</script>

<style lang="css" scoped>
#meetings {
  box-sizing: border-box;
  overflow-x: hidden;
  overflow-y: auto;
  width: 100%;
}

.title-meeting {
    font-weight: 600;
    color: #292d32de
}

.bg-wrap-join {
  background-color: #1212a1;
  position: relative;
}

.bg-wrap-join .title-meeting {
  color: rgb(238, 230, 230);
}

.btn-join {
    color: rgb(238, 230, 230);
    background-color: #6b6bb6;
    font-weight: 600;
    padding-left: 20px;
    padding-right: 20px;
}

.btn-join:hover {
    background-color: #4f4fa8
}

.time-meeting {
  text-align: center;
  position: absolute;
  right: 15px;
  top:10px;
  color: #daf6ff;
  text-shadow: 0 0 20px rgba(10, 175, 230, 1), 0 0 20px rgba(10, 175, 230, 0);
  letter-spacing: 0.05em;
}
</style>
