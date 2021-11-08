<template lang="">
  <div>
    <section class="headline">
      <div class="introduce">
        <h1>Cuộc họp video chất lượng. Giờ đây miễn phí cho tất cả mọi người.</h1>
        <p>
          Chúng tôi đã thiết kế lại Video Call - dịch vụ tổ chức cuộc họp
          kinh doanh với độ bảo mật cao - để cung cấp miễn phí cho mọi người.
        </p>
        <div class="wrapper-button">
          <a
            id="login"
            href="#"
            class="singin"
            @click="toggleModal"
          >
            <span>Đăng nhập</span>
          </a>
          <a
            id="register"
            href="#"
            class="singup"
            @click="toggleSignUp"
          >
            <span>Đăng kí</span>
          </a>
        </div>
      </div>
      <picture class="banner-introduce">
        <img
          src="@/assets/images/banner-team-1-copy.png"
          loading="lazy"
          alt="banner-introduce"
        >
      </picture>
    </section>
    <ModalSignIn
      :modal="modal"
      @changeIsSignUp="changeIsSignUp"
      @closeModal="closeModal"
    />
  </div>
</template>
<script>
import ModalSignIn from '../../modal/ModalSignIn.vue'
export default {
    name: 'HomeBanner',
    components: {
        ModalSignIn,
    },
    data() {
        return {
            modal: {
                isShowModal: false,
                isSignUp: false,
            }
        }
    },
    methods: {
        toggleModal: function() {
            this.modal.isShowModal = true
            this.modal.isSignUp = false
        },

        toggleSignUp: function() {
            this.modal.isSignUp = true
            this.modal.isShowModal = true
        },

        changeIsSignUp: function(toggle) {
            this.modal.isSignUp = toggle
        },

        closeModal: function() {
            this.modal.isShowModal = false
        }
    }
}
</script>
<style lang="css" scoped>
.headline {
    width: 100%;
    max-width: 100%;
    overflow: hidden;
    height: calc(100vh - 55px);
    background:
        linear-gradient(to right, rgba(108, 8, 202, 0.726), rgba(7, 70, 187, 0.808), rgba(29, 213, 219, 0.781)),
        url(../../../assets/images/home-banner.jpg) left top no-repeat;
    background-size: cover;
    display: grid;
    grid-template-columns: 1fr 1fr;
    align-items: center;
}
.introduce {
    padding: 0 50px;
}

.introduce h1 {
    margin: 1rem 2rem;
}

.banner-introduce {
    display: flex;
    justify-content: center;
}

.banner-introduce img {
    max-width: 100%;
}

.introduce .wrapper-button {
    margin: auto;
    display: flex;
    justify-content: center;
    align-items: center;
}

/* SECTIONS */

.wrapper-button a {
    padding: 10px 1rem;
    color: #fff;
    text-decoration: none;
    margin: 0.5rem;
    border-radius: 50px;
    box-shadow: 0 5px 2px rgba(0, 0, 0, 0.2);
    font-weight: 400;
}

.wrapper-button a.singin {
    background: linear-gradient(to right, #20ddb1, #19a0d1);
    color:rgba(7, 70, 187, 0.808);
    font-weight: 600;
}

.wrapper-button a.singup {
    border: 1px solid #fff;
    background-color: transparent;
    color: #fff;
}

.wrapper-button a:hover {
    background: linear-gradient(to right, #EE7752, #E73C7E, #23A6D5, #23D5AB);
    animation: bounceButton .3s ease infinite alternate;
}

@keyframes bounceButton {
    100% {
        transform: translateY(-20px);
    }
}

/* Mobile Devices - Phones/Tablets */

@media only screen and (max-width: 992px) {
    .headline {
        grid-template-columns: none !important;
    }

}

</style>