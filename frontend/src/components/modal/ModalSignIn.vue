<template>
  <div :class="'modal ' + (modal.isShowModal ? 'show-modal' : '')">
    <div class="modal-content">
      <span class="close-button" @click="closeModal">&times;</span>
      <div
        id="container"
        :class="'container ' + (modal.isSignUp ? 'right-panel-active' : '')"
      >
        <div class="form-container sign-up-container">
          <form action="" @submit.prevent="hanldeSubmitSignUp">
            <h3>Create Account</h3>
            <div class="social-container">
              <!-- <a href="#" class="social"><font-awesome-icon icon="fa-brands fa-twitter" /></a> -->
              <a href="#" class="social"><i class="ti-google" /></a>
              <a href="#" class="social"><i class="ti-facebook" /></a>
            </div>
            <span>or use your email for registration</span>
            <input
              v-model.trim="fullName"
              type="text"
              placeholder="Full Name"
            />
            <span
              v-if="errors['fullName'] && !errors['fullName'].checked"
              class="error text-danger m-0"
              >{{ errors["fullName"].message }}</span
            >
            <input
              v-model.trim="username"
              type="text"
              minlength="3"
              placeholder="Username"
            />
            <span
              v-if="errors['username'] && !errors['username'].checked"
              class="error text-danger m-0"
              >{{ errors["username"].message }}</span
            >
            <input v-model.trim="email" type="email" placeholder="Email" />
            <span
              v-if="errors['email'] && !errors['email'].checked"
              class="error text-danger m-0"
              v-text="errors['email'].message"
            />
            <!-- >{{ errors['email'].message }}</span> -->
            <input
              v-model.trim="password"
              type="password"
              placeholder="Password"
            />
            <span
              v-if="errors['password'] && !errors['password'].checked"
              class="error text-danger m-0"
              >{{ errors["password"].message }}</span
            >
            <input
              v-model.trim="confirmPassword"
              type="password"
              placeholder="Confirm Password"
              @keydown.enter="hanldeSubmitSignUp"
            />
            <span
              v-if="
                errors['confirmPassword'] && !errors['confirmPassword'].checked
              "
              class="error text-danger m-0"
              >{{ errors["confirmPassword"].message }}</span
            >
            <input class="btn-submit" type="submit" value="Sign Up" />
            <input type="button" class="ghost btn-submit" value="Sign In" />
          </form>
        </div>
        <div class="form-container sign-in-container">
          <form action="" @submit.prevent="handleSubmitSignIn">
            <h2>Sign in</h2>
            <div class="social-container">
              <a href="#" class="social"><i class="ti-facebook" /></a>
              <a href="#" class="social"><i class="ti-google" /></a>
              <a href="#" class="social"><i class="ti-linkedin" /></a>
            </div>
            <span>or use your account</span>
            <input
              v-model.trim="username"
              type="text"
              minlength="3"
              placeholder="Username or email"
            />
            <span
              v-if="errors['username'] && !errors['username'].checked"
              class="error text-danger m-0"
              >{{ errors["username"].message }}</span
            >
            <input
              v-model.trim="password"
              type="password"
              placeholder="Password"
            />
            <span
              v-if="errors['password'] && !errors['password'].checked"
              class="error text-danger m-0"
              >{{ errors["password"].message }}</span
            >
            <a class="forgot-pass" href="#">Forgot your password?</a>
            <input class="btn-submit" type="submit" value="Sign In" />
            <input
              type="button"
              class="ghost btn-submit"
              value="Sign Up"
              @click="tabSignUp"
            />
          </form>
        </div>
        <div class="overlay-container">
          <div class="overlay">
            <div class="overlay-panel overlay-left">
              <h3>Welcome Back!</h3>
              <p class="text-p">
                To keep connected with us please login with your personal info
              </p>
              <button class="ghost btn-submit" @click="tabSignIn">
                Sign In
              </button>
            </div>
            <div class="overlay-panel overlay-right">
              <h3>Hello, Friend!</h3>
              <p class="text-p">
                Enter your personal details and start journey with us
              </p>
              <button class="ghost btn-submit" @click="tabSignUp">
                Sign Up
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  name: "ModalSignIn",
  props: {
    modal: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      username: null,
      password: null,
      email: null,
      confirmPassword: null,
      fullName: null,
      errors: {},
    };
  },

  computed: {
    ...mapGetters("users", ["getRoles"]),
  },

  watch: {
    username(value) {
      this.username = value;
      this.validUsername();
    },
    email(value) {
      this.email = value;
      this.validEmail();
    },
    password(value) {
      this.password = value;
      this.validPassword();
    },
    confirmPassword(value) {
      this.cofirmPassword = value;
      this.validConfirmPassword();
    },
    fullName(value) {
      this.fullName = value;
      this.validFullName();
    },
  },

  methods: {
    ...mapActions("users", ["createUser", "login"]),

    tabSignUp: function () {
      this.$emit("changeIsSignUp", true);
    },

    tabSignIn: function () {
      this.$emit("changeIsSignUp", false);
    },

    closeModal: function () {
      this.$emit("closeModal");
    },

    validUsername() {
      if (!this.username) {
        this.errors["username"] = {
          checked: false,
          message: "Username required",
        };
      } else if (this.username.length < 3) {
        this.errors["username"] = {
          checked: false,
          message: "Username least must 3 characters",
        };
      } else
        this.errors["username"] = {
          checked: true,
        };
    },

    validFullName() {
      if (!this.fullName) {
        this.errors["fullName"] = {
          checked: false,
          message: "FullName required",
        };
      } else
        this.errors["fullName"] = {
          checked: true,
        };
    },

    validEmail() {
      const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
      if (!this.email) {
        this.errors["email"] = {
          checked: false,
          message: "Email required",
        };
      } else if (this.email) {
        this.errors["email"] = {
          checked: emailPattern.test(this.email),
          message: "Email invalid",
        };
      }
    },

    validPassword() {
      if (!this.password) {
        this.errors["password"] = {
          checked: false,
          message: "Password required",
        };
      } else
        this.errors["password"] = {
          checked: true,
        };
    },

    validConfirmPassword() {
      if (!this.confirmPassword) {
        this.errors["confirmPassword"] = {
          checked: false,
          message: "ConfirmPassword required",
        };
      } else {
        this.errors["confirmPassword"] = {
          checked: this.password === this.confirmPassword,
          message: "Confirm password not match",
        };
      }
    },

    async hanldeSubmitSignUp() {
      this.validUsername();
      this.validPassword();
      this.validConfirmPassword();
      this.validEmail();
      this.validFullName();
      if (
        this.errors["username"].checked &&
        this.errors["password"].checked &&
        this.errors["email"].checked &&
        this.errors["confirmPassword"].checked &&
        this.errors["fullName"].checked
      ) {
        try {
          await this.createUser({
            username: this.username,
            password: this.password,
            email: this.email,
            confirmPassword: this.confirmPassword,
            fullName: this.fullName,
          });

          this.$toast.success("Register success! Please verify email.", {
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
            rtl: false,
          });
          this.$emit("closeModal");
        } catch (errs) {
          if (errs.response.status === 400) {
            errs.response.data.errors.forEach((err) => {
              this.errors[`${err.source}`] = {
                checked: false,
                message: err.message,
              };
            });
          }
        }
      }
    },

    async handleSubmitSignIn() {
      this.validUsername();
      this.validPassword();
      if (this.errors["username"].checked && this.errors["password"].checked) {
        try {
          console.log(process.env.VUE_APP_SERVICE_ENDPOINT);
          await this.login({
            username: this.username,
            password: this.password,
          });
          this.$emit("closeModal");
          const roleAdminIdx = this.getRoles.findIndex(
            (r) => r.roleName === "ROLE_ADMIN"
          );
          if (roleAdminIdx != -1) {
            this.$router.push({ path: "/admin" });
          } else {
            this.$router.push({ path: "/conversations/teams" });
          }
        } catch (errs) {
          if (errs.response.status === 404) {
            this.$toast.error(errs.response.data.message, {
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
              rtl: false,
            });
          }

          if (errs.response.status === 401) {
            this.$toast.error("Username or password invalid", {
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
              rtl: false,
            });
          }
        }
      }
    },
  },
};
</script>

<style lang="css">
form input {
  margin: 4px 0;
  padding: 8px 15px;
}

.error {
  font-size: 12px;
}
</style>
