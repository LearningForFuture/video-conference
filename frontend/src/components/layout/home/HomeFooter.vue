<template lang="">
  <footer class="footer-section">
    <div class="container-home">
      <div class="footer-cta">
        <div class="row">
          <div class="single-cta">
            <font-awesome-icon
              color="orange"
              :icon="['fas', 'map-marker-alt']"
            />
            <div class="cta-text">
              <h4>Find us</h4>
              <span>Duy phuoc, Duy Xuyen village, Quang Nam province</span>
            </div>
          </div>
          <div class="single-cta">
            <i class="fas fa-phone" />
            <font-awesome-icon
              color="green"
              :icon="['fas', 'phone']"
            />
            <div class="cta-text">
              <h4>Call us</h4>
              <span>9876543210 0</span>
            </div>
          </div>
          <div class="single-cta">
            <font-awesome-icon
              color="orange"
              :icon="['fas', 'envelope-open']"
            />
            <div class="cta-text">
              <h4>Mail us</h4>
              <span>mail@info.com</span>
            </div>
          </div>
        </div>
      </div>
      <div class="footer-content">
        <div class="row">
          <div class="">
            <div class="footer-widget">
              <div class="footer-logo">
                <a href="index.html"><img
                  src="@/assets/images/Video-Conferencing-App-Logo.png"
                  class="img-fluid"
                  alt="logo"
                  loading="lazy"
                ></a>
              </div>
              <div class="footer-text">
                <p>
                  Lorem ipsum dolor sit amet, consec tetur adipisicing elit, sed do eiusmod tempor
                  incididuntut consec tetur adipisicing
                  elit,Lorem ipsum dolor sit amet.
                </p>
              </div>
              <div class="footer-logo">
                <a href="index.html"><img
                  src="https://lh3.googleusercontent.com/proxy/f8a-YJr2vmbI75Yjx1mvkMyE52jm9VxJ-ECv1ZOsyBwtVv6p9fGAEBvY1nbt9zHbhn0n21Kk7g9tMFHbpKTLxq-NQTD_Em4EMID0MPcovtjJtO0xEUOi7_8tcInzFhh0ztPD"
                  class="img-fluid"
                  alt="logo"
                  loading="lazy"
                ></a>
                <a href="index.html"><img
                  src="data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAoHCBMVExcTExQTFxcYFxcSGBoXFB4XFxMXFxcbGhoYFRUcJSwkGyApHhcaJTYlKS4yMzMzGiY5PjkxPSwyMzABCwsLEA4QHhISHTgqJCo9MjI9MD07MDUwMjA1MjIyMjYyMjI0MjQwMjIyMjIyMjIyMDIwMjIwNDAyMjI0MjIyM
                                    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
                                    bxDaO9GIbR3oOPzpWZNU2c+KnjdI8yRkNbdeQ115OlfLNPZU9LZ+5VEbo37q92F11+Ehtx0dhXa4htHejENo70GSwfqPYV7iG0d6xe4XHSNR50CdScY9p9qa/Izk6i8kpvBYl
                                    Qk4x7T7U1+RnJ1F5JTeCxBNIQhAJM05iht6tndBovVY/dQKdjO0969xu2nvTYb1bO6DReqx+6jerZ3QaL1WP3UCn43bT3oxu2nvTYb1bO6DReqx+6jerZ3QaL1WP3UCn43bT3oxu
                                    2nvTYb1bO6DReqx+6jerZ3QaL1WP3UCn43bT3oxu2nvTYb1bO6DReqx+6jerZ3QaL1WP3UCn43bT3oxu2nvTYb1bO6DReqx+6jerZ3QaL1WP3UCn43bT3oxu2nvTYb1bO6DReqx+6
                                    jerZ3QaL1WP3UCn43bT3oxu2nvTYb1bO6DReqx+6jerZ3QaL1WP3UCn43bT3oxu2nvTYb1bO6DReqx+6jerZ3QaL1WP3UCn43bT3oxu2nvTYb1bO6DReqx+6jerZ3QaL1WP3U
                                    Cn43bT3oxu2nvTYb1bO6DReqx+6jerZ3QaL1WP3UCn43bT3oxu2nvTYb1bO6DReqx+6jerZ3QaL1WP3UCn43bT3oxu2nvTYb1bO6DReqx+6jerZ3QaL1WP3UCmJssjOTqLySm8Fi9
                                    3q2d0Gi9Vj91ScELWNDGtDWtAa1rQA1rQLgGgaAANFyD7IQhAIQhAIQhAIQhAIQhAIQhAIQhAIQhAIQhAIQhAIQhAIQhAIQhAIQhAIQhAIQhB//9k="
                  class="img-fluid"
                  alt="logo"
                  loading="lazy"
                ></a>
              </div>
              <div class="footer-social-icon">
                <span>Follow us</span>
                <a href="#">
                  <font-awesome-icon
                    class="facebook-bg"
                    :icon="{ prefix: 'fab', iconName: 'facebook' }"
                  /></a>
                <a href="#">
                  <font-awesome-icon
                    color="blue"
                    :icon="{ prefix: 'fab', iconName: 'twitter' }"
                  /></a>
                <a href="#">
                  <font-awesome-icon
                    color="red"
                    :icon="{ prefix: 'fab', iconName: 'google' }"
                  /></a>
              </div>
            </div>
          </div>
          <div class="">
            <div class="footer-widget">
              <div class="footer-widget-heading">
                <h3>Useful Links</h3>
              </div>
              <ul>
                <li><a href="#">Home</a></li>
                <li><a href="#">about</a></li>
                <li><a href="#">services</a></li>
                <li><a href="#">portfolio</a></li>
                <li><a href="#">Contact</a></li>
                <li><a href="#">About us</a></li>
                <li><a href="#">Our Services</a></li>
                <li><a href="#">Expert Team</a></li>
                <li><a href="#">Contact us</a></li>
                <li><a href="#">Latest News</a></li>
              </ul>
            </div>
          </div>
          <div class="">
            <div class="footer-widget">
              <div class="footer-widget-heading">
                <h3>Subscribe</h3>
              </div>
              <div class="footer-text mb-25">
                <p>Don’t miss to subscribe to our new feeds, kindly fill the form below.</p>
              </div>
              <div class="subscribe-form">
                <form action="#">
                  <input
                    type="text"
                    placeholder="Email Address"
                  >
                  <button>
                    <font-awesome-icon :icon="{prefix: 'fab', iconName: 'telegram'}" />
                  </button>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="copyright-area">
      <div class="container-home">
        <div class="row">
          <div class="">
            <div class="copyright-text">
              <p>Copyright &copy; 2021, All Right Reserved <a href="#">DUT Team</a></p>
            </div>
          </div>
          <div class="">
            <div class="footer-menu">
              <ul>
                <li><a href="#">Home</a></li>
                <li><a href="#">Terms</a></li>
                <li><a href="#">Privacy</a></li>
                <li><a href="#">Policy</a></li>
                <li><a href="#">Contact</a></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  name: 'HomeFooter'
}
</script>

<style lang="css">
svg {
    width: 35px;
    height: 35px;
}

.facebook-bg {
    border-radius: 50%;
}

.subscribe-form button {
    display: flex;
    justify-content: center;
    align-items: center;
    max-height: 45px;
}

button svg {
    height: 30px !important;
    color: #fff;
}

.single-cta {
    display: flex;
    justify-content:center;
    align-items: center;
}

@media screen and(max-width: 992px) {
    svg {
        width: 20px;
        height: 20px;
    }
}
</style>
