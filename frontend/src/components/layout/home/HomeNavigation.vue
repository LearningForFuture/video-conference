<template lang="">
  <div class="nav-wrapper">
    <div class="grad-bar" />
    <nav class="navbar">
      <div class="logo">
        <img
          src="@/assets/images/Video-Conferencing-App-Logo.png"
          alt="Company Logo"
          loading="lazy"
        >
        <div class="wrapper">
          <div class="bg">
            Video Call
          </div>
          <div class="fg">
            Video Call
          </div>
        </div>
      </div>
      <div
        id="mobile-menu"
        :class="'menu-toggle ' + (isactive ? 'is-active': '')"
        @click="ismobile =! ismobile; isactive =! isactive"
      >
        <span class="bar" />
        <span class="bar" />
        <span class="bar" />
      </div>
      <ul :class="'nav ' + (ismobile ? 'mobile-nav' : '')">
        <li
          v-for="(item, index) in dataNav"
          :key="index"
          class="nav-item"
        >
          <router-link
            tag="a"
            :to="{path:item.to}"
          >
            <span>{{ item.text }}</span>
          </router-link>
        </li>
      </ul>
    </nav>
  </div>
</template>

<script>
export default {
  name: 'HomeNavigation',
  data() {
    return {
      dataNav: [
        { text: "Trang chủ", to: "/"},
        { text: "Dịch vụ", to: "/services"},
        { text: "Bài viết", to: "/blogs"},
        { text: "Liên hệ", to: "/contact"}
      ],
      ismobile: false,
      isactive: false,
    }
  }
}
</script>

<style scoped lang="css">
.nav-wrapper {
    width: 100%;
    position: -webkit-sticky;
    /* Safari */
    position: sticky;
    top: 0;
    background-color: #fff;
    z-index: 999;
}

.grad-bar {
    width: 100%;
    height: 5px;
    background: linear-gradient(-45deg, #EE7752, #E73C7E, #23A6D5, #23D5AB);
    background-size: 400% 400%;
    -webkit-animation: gradbar 15s ease infinite;
    -moz-animation: gradbar 15s ease infinite;
    animation: gradbar 15s ease infinite;
}

/* NAVIGATION */

.navbar {
    display: grid;
    grid-template-columns: 1fr 3fr;
    justify-self: center;
    justify-content:center;
    height: 50px;
    overflow: hidden;
}

.navbar .logo {
    justify-self: center;
    display: flex;
    flex-flow: row nowrap;
    justify-content: center;
    align-items: center;
}

.navbar .logo img {
    height: 28px;
    width: auto;
}

/* Text Logo */
.navbar .logo .wrapper {
    display: grid;
    margin-left: 0.5rem;
    grid-template-areas: 'overlap';
    place-content: center;
    text-transform: uppercase;
    height: auto;
}

.navbar .logo .wrapper>div {
    background-clip: text;
    -webkit-background-clip: text;
    color: #fff;
    font-family: 'Poppins', sans-serif;
    font-weight: 900;
    font-size: 22px;
    grid-area: overlap;
    letter-spacing: 1px;
    -webkit-text-stroke: 4px transparent;
}

.navbar .logo .wrapper>div.bg {
    background-image: repeating-linear-gradient(105deg, var(--gold) 0%, var(--dark-shadow) 1%,
            var(--gold) 12%);
    color: transparent;
    transform: scaleY(1.05);
    transform-origin: top;
}

.navbar .logo .wrapper>div.fg {
    background-image: repeating-linear-gradient(5deg, var(--gold) 0%, var(--light-shadow) 23%,
            var(--gold) 31%);
    transform: scale(1);
}

.navbar ul {
    list-style: none;
    display: grid;
    grid-template-columns: repeat(5, 1fr);
    justify-self: end;
    height: 100%;
}

.nav-item a {
    color: #000;
    font-size: 0.9rem;
    font-weight: 600;
    text-decoration: none;
    transition: color 0.3s ease-out;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    padding: 0 0.5rem;
    box-sizing: border-box;
}

.nav-item a span {
    position: relative;
}

.nav-item a span:after,
.nav-item a span:before {
    transition: all .5s;
}

.nav-item a span:after {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    margin: auto;
    width: 0%;
    content: '.';
    color: transparent;
    background: linear-gradient(to right, #EE7752, #E73C7E, #23A6D5, #23D5AB);
    height: 5px;
    filter: opacity(50%);
}

.nav-item a:hover>span:after {
    width: 100%;
}

.nav-item a:hover {
    color: #3498db;
}

/* MOBILE MENU & ANIMATION */

.menu-toggle .bar {
    width: 25px;
    height: 3px;
    background-color: #3f3f3f;
    margin: 5px auto;
    -webkit-transition: all 0.3s ease-in-out;
    -o-transition: all 0.3s ease-in-out;
    transition: all 0.3s ease-in-out;
}

.menu-toggle {
    justify-self: end;
    margin-right: 25px;
    display: none;
}

.menu-toggle:hover {
    cursor: pointer;
}

#mobile-menu.is-active .bar:nth-child(2) {
    opacity: 0;
}

#mobile-menu.is-active .bar:nth-child(1) {
    -webkit-transform: translateY(8px) rotate(45deg);
    -ms-transform: translateY(8px) rotate(45deg);
    -o-transform: translateY(8px) rotate(45deg);
    transform: translateY(8px) rotate(45deg);
}

#mobile-menu.is-active .bar:nth-child(3) {
    -webkit-transform: translateY(-8px) rotate(-45deg);
    -ms-transform: translateY(-8px) rotate(-45deg);
    -o-transform: translateY(-8px) rotate(-45deg);
    transform: translateY(-8px) rotate(-45deg);
}

/* KEYFRAME ANIMATIONS */

@-webkit-keyframes gradbar {
    0% {
        background-position: 0% 50%
    }

    50% {
        background-position: 100% 50%
    }

    100% {
        background-position: 0% 50%
    }
}

@-moz-keyframes gradbar {
    0% {
        background-position: 0% 50%
    }

    50% {
        background-position: 100% 50%
    }

    100% {
        background-position: 0% 50%
    }
}

@keyframes gradbar {
    0% {
        background-position: 0% 50%
    }

    50% {
        background-position: 100% 50%
    }

    100% {
        background-position: 0% 50%
    }
}

@media only screen and (max-width: 992px) {
    .navbar {
        grid-template-columns: 2fr 3fr;
    }

}

@media only screen and (max-width: 768px) {
    .features {
        flex-direction: column;
        padding: 50px;
    }

    /* MOBILE NAVIGATION */

    .navbar {
        grid-template-columns: 3fr 1fr;
    }

    .navbar ul {
        display: flex;
        flex-direction: column;
        position: fixed;
        justify-content: start;
        top: 55px;
        background-color: #fff;
        width: 100%;
        height: calc(100vh - 55px);
        transform: translate(-101%);
        text-align: center;
        overflow: hidden;
    }

    .navbar li {
        padding: 15px;
    }

    .navbar li:first-child {
        margin-top: 50px;
    }

    .navbar li a {
        font-size: 1rem;
    }

    .menu-toggle,
    .bar {
        display: block;
        cursor: pointer;
    }

    .mobile-nav {
        transform: translate(0%) !important;
    }
}

</style>
