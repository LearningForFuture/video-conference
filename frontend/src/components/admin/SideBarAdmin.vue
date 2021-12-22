<template>
  <!-- BEGIN: Side Menu -->
  <nav class="side-nav">
    <a href="" class="intro-x flex items-center pl-5 pt-4">
      <img
        alt="Midone Tailwind HTML Admin Template"
        class="w-6"
        src="@/assets/images/logo.svg"
      />
      <span class="hidden xl:block text-white text-lg ml-3">
        Mid<span class="font-medium">one</span>
      </span>
    </a>
    <div class="side-nav__devider my-6" />
    <ul>
      <li>
        <a class="side-menu side-menu--active" @click="navigateHomePage">
          <div class="side-menu__icon">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="1.5"
              stroke-linecap="round"
              stroke-linejoin="round"
              class="feather feather-home"
            >
              <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z" />
              <polyline points="9 22 9 12 15 12 15 22" />
            </svg>
          </div>
          <div class="side-menu__title">Dashboard</div>
        </a>
      </li>
      <li>
        <a href="javascript:;" class="side-menu" @click="navigateRoomPage">
          <div class="side-menu__icon">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="1.5"
              stroke-linecap="round"
              stroke-linejoin="round"
              class="feather feather-box"
            >
              <path
                d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16z"
              />
              <polyline points="3.27 6.96 12 12.01 20.73 6.96" />
              <line x1="12" y1="22.08" x2="12" y2="12" />
            </svg>
          </div>
          <div class="side-menu__title">
            Room
            <div class="side-menu__sub-icon">
              <i data-feather="chevron-down" />
            </div>
          </div>
        </a>
      </li>
      <li>
        <a href="side-menu-light-inbox.html" class="side-menu">
          <div class="side-menu__icon"><i data-feather="inbox" /></div>
          <div class="side-menu__title">Inbox</div>
        </a>
      </li>
      <li>
        <a href="side-menu-light-file-manager.html" class="side-menu">
          <div class="side-menu__icon"><i data-feather="hard-drive" /></div>
          <div class="side-menu__title">File Manager</div>
        </a>
      </li>
      <li>
        <a href="side-menu-light-point-of-sale.html" class="side-menu">
          <div class="side-menu__icon"><i data-feather="credit-card" /></div>
          <div class="side-menu__title">Point of Sale</div>
        </a>
      </li>
      <li>
        <a href="side-menu-light-chat.html" class="side-menu">
          <div class="side-menu__icon">
            <i data-feather="message-square" />
          </div>
          <div class="side-menu__title">Chat</div>
        </a>
      </li>
      <li>
        <a href="side-menu-light-post.html" class="side-menu">
          <div class="side-menu__icon"><i data-feather="file-text" /></div>
          <div class="side-menu__title">Post</div>
        </a>
      </li>
      <li>
        <a href="side-menu-light-calendar.html" class="side-menu">
          <div class="side-menu__icon"><i data-feather="calendar" /></div>
          <div class="side-menu__title">Calendar</div>
        </a>
      </li>
      <li class="side-nav__devider my-6" />
      <li>
        <a href="javascript:;" class="side-menu">
          <div class="side-menu__icon"><i data-feather="edit" /></div>
          <div class="side-menu__title">
            Crud
            <div class="side-menu__sub-icon">
              <i data-feather="chevron-down" />
            </div>
          </div>
        </a>
        <ul class="">
          <li>
            <a href="side-menu-light-crud-data-list.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Data List</div>
            </a>
          </li>
          <li>
            <a href="side-menu-light-crud-form.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Form</div>
            </a>
          </li>
        </ul>
      </li>
      <li>
        <a href="javascript:;" class="side-menu">
          <div class="side-menu__icon"><i data-feather="users" /></div>
          <div class="side-menu__title">
            Users
            <div class="side-menu__sub-icon">
              <i data-feather="chevron-down" />
            </div>
          </div>
        </a>
        <ul class="">
          <li>
            <a href="side-menu-light-users-layout-1.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Layout 1</div>
            </a>
          </li>
          <li>
            <a href="side-menu-light-users-layout-2.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Layout 2</div>
            </a>
          </li>
          <li>
            <a href="side-menu-light-users-layout-3.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Layout 3</div>
            </a>
          </li>
        </ul>
      </li>
      <li>
        <a href="javascript:;" class="side-menu">
          <div class="side-menu__icon"><i data-feather="trello" /></div>
          <div class="side-menu__title">
            Profile
            <div class="side-menu__sub-icon">
              <i data-feather="chevron-down" />
            </div>
          </div>
        </a>
        <ul class="">
          <li>
            <a href="side-menu-light-profile-overview-1.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Overview 1</div>
            </a>
          </li>
          <li>
            <a href="side-menu-light-profile-overview-2.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Overview 2</div>
            </a>
          </li>
          <li>
            <a href="side-menu-light-profile-overview-3.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Overview 3</div>
            </a>
          </li>
        </ul>
      </li>
      <li>
        <a href="javascript:;" class="side-menu">
          <div class="side-menu__icon"><i data-feather="layout" /></div>
          <div class="side-menu__title">
            Pages
            <div class="side-menu__sub-icon">
              <i data-feather="chevron-down" />
            </div>
          </div>
        </a>
        <ul class="">
          <li>
            <a href="javascript:;" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">
                Wizards
                <div class="side-menu__sub-icon">
                  <i data-feather="chevron-down" />
                </div>
              </div>
            </a>
            <ul class="">
              <li>
                <a
                  href="side-menu-light-wizard-layout-1.html"
                  class="side-menu"
                >
                  <div class="side-menu__icon"><i data-feather="zap" /></div>
                  <div class="side-menu__title">Layout 1</div>
                </a>
              </li>
              <li>
                <a
                  href="side-menu-light-wizard-layout-2.html"
                  class="side-menu"
                >
                  <div class="side-menu__icon"><i data-feather="zap" /></div>
                  <div class="side-menu__title">Layout 2</div>
                </a>
              </li>
              <li>
                <a
                  href="side-menu-light-wizard-layout-3.html"
                  class="side-menu"
                >
                  <div class="side-menu__icon"><i data-feather="zap" /></div>
                  <div class="side-menu__title">Layout 3</div>
                </a>
              </li>
            </ul>
          </li>
          <li>
            <a href="javascript:;" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">
                Blog
                <div class="side-menu__sub-icon">
                  <i data-feather="chevron-down" />
                </div>
              </div>
            </a>
            <ul class="">
              <li>
                <a href="side-menu-light-blog-layout-1.html" class="side-menu">
                  <div class="side-menu__icon"><i data-feather="zap" /></div>
                  <div class="side-menu__title">Layout 1</div>
                </a>
              </li>
              <li>
                <a href="side-menu-light-blog-layout-2.html" class="side-menu">
                  <div class="side-menu__icon"><i data-feather="zap" /></div>
                  <div class="side-menu__title">Layout 2</div>
                </a>
              </li>
              <li>
                <a href="side-menu-light-blog-layout-3.html" class="side-menu">
                  <div class="side-menu__icon"><i data-feather="zap" /></div>
                  <div class="side-menu__title">Layout 3</div>
                </a>
              </li>
            </ul>
          </li>
          <li>
            <a href="javascript:;" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">
                Pricing
                <div class="side-menu__sub-icon">
                  <i data-feather="chevron-down" />
                </div>
              </div>
            </a>
            <ul class="">
              <li>
                <a
                  href="side-menu-light-pricing-layout-1.html"
                  class="side-menu"
                >
                  <div class="side-menu__icon"><i data-feather="zap" /></div>
                  <div class="side-menu__title">Layout 1</div>
                </a>
              </li>
              <li>
                <a
                  href="side-menu-light-pricing-layout-2.html"
                  class="side-menu"
                >
                  <div class="side-menu__icon"><i data-feather="zap" /></div>
                  <div class="side-menu__title">Layout 2</div>
                </a>
              </li>
            </ul>
          </li>
          <li>
            <a href="javascript:;" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">
                Invoice
                <div class="side-menu__sub-icon">
                  <i data-feather="chevron-down" />
                </div>
              </div>
            </a>
            <ul class="">
              <li>
                <a
                  href="side-menu-light-invoice-layout-1.html"
                  class="side-menu"
                >
                  <div class="side-menu__icon"><i data-feather="zap" /></div>
                  <div class="side-menu__title">Layout 1</div>
                </a>
              </li>
              <li>
                <a
                  href="side-menu-light-invoice-layout-2.html"
                  class="side-menu"
                >
                  <div class="side-menu__icon"><i data-feather="zap" /></div>
                  <div class="side-menu__title">Layout 2</div>
                </a>
              </li>
            </ul>
          </li>
          <li>
            <a href="javascript:;" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">
                FAQ
                <div class="side-menu__sub-icon">
                  <i data-feather="chevron-down" />
                </div>
              </div>
            </a>
            <ul class="">
              <li>
                <a href="side-menu-light-faq-layout-1.html" class="side-menu">
                  <div class="side-menu__icon"><i data-feather="zap" /></div>
                  <div class="side-menu__title">Layout 1</div>
                </a>
              </li>
              <li>
                <a href="side-menu-light-faq-layout-2.html" class="side-menu">
                  <div class="side-menu__icon"><i data-feather="zap" /></div>
                  <div class="side-menu__title">Layout 2</div>
                </a>
              </li>
              <li>
                <a href="side-menu-light-faq-layout-3.html" class="side-menu">
                  <div class="side-menu__icon"><i data-feather="zap" /></div>
                  <div class="side-menu__title">Layout 3</div>
                </a>
              </li>
            </ul>
          </li>
          <li>
            <a href="login-light-login.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Login</div>
            </a>
          </li>
          <li>
            <a href="login-light-register.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Register</div>
            </a>
          </li>
          <li>
            <a href="main-light-error-page.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Error Page</div>
            </a>
          </li>
          <li>
            <a href="side-menu-light-update-profile.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Update profile</div>
            </a>
          </li>
          <li>
            <a href="side-menu-light-change-password.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Change Password</div>
            </a>
          </li>
        </ul>
      </li>
      <li class="side-nav__devider my-6" />
      <li>
        <a href="javascript:;" class="side-menu">
          <div class="side-menu__icon"><i data-feather="inbox" /></div>
          <div class="side-menu__title">
            Components
            <div class="side-menu__sub-icon">
              <i data-feather="chevron-down" />
            </div>
          </div>
        </a>
        <ul class="">
          <li>
            <a href="javascript:;" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">
                Table
                <div class="side-menu__sub-icon">
                  <i data-feather="chevron-down" />
                </div>
              </div>
            </a>
            <ul class="">
              <li>
                <a href="side-menu-light-regular-table.html" class="side-menu">
                  <div class="side-menu__icon"><i data-feather="zap" /></div>
                  <div class="side-menu__title">Regular Table</div>
                </a>
              </li>
              <li>
                <a href="side-menu-light-tabulator.html" class="side-menu">
                  <div class="side-menu__icon"><i data-feather="zap" /></div>
                  <div class="side-menu__title">Tabulator</div>
                </a>
              </li>
            </ul>
          </li>
          <li>
            <a href="javascript:;" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">
                Overlay
                <div class="side-menu__sub-icon">
                  <i data-feather="chevron-down" />
                </div>
              </div>
            </a>
            <ul class="">
              <li>
                <a href="side-menu-light-modal.html" class="side-menu">
                  <div class="side-menu__icon"><i data-feather="zap" /></div>
                  <div class="side-menu__title">Modal</div>
                </a>
              </li>
              <li>
                <a href="side-menu-light-slide-over.html" class="side-menu">
                  <div class="side-menu__icon"><i data-feather="zap" /></div>
                  <div class="side-menu__title">Slide Over</div>
                </a>
              </li>
              <li>
                <a href="side-menu-light-notification.html" class="side-menu">
                  <div class="side-menu__icon"><i data-feather="zap" /></div>
                  <div class="side-menu__title">Notification</div>
                </a>
              </li>
            </ul>
          </li>
          <li>
            <a href="side-menu-light-accordion.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Accordion</div>
            </a>
          </li>
          <li>
            <a href="side-menu-light-button.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Button</div>
            </a>
          </li>
          <li>
            <a href="side-menu-light-alert.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Alert</div>
            </a>
          </li>
          <li>
            <a href="side-menu-light-progress-bar.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Progress Bar</div>
            </a>
          </li>
          <li>
            <a href="side-menu-light-tooltip.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Tooltip</div>
            </a>
          </li>
          <li>
            <a href="side-menu-light-dropdown.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Dropdown</div>
            </a>
          </li>
          <li>
            <a href="side-menu-light-typography.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Typography</div>
            </a>
          </li>
          <li>
            <a href="side-menu-light-icon.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Icon</div>
            </a>
          </li>
          <li>
            <a href="side-menu-light-loading-icon.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Loading Icon</div>
            </a>
          </li>
        </ul>
      </li>
      <li>
        <a href="javascript:;" class="side-menu">
          <div class="side-menu__icon"><i data-feather="sidebar" /></div>
          <div class="side-menu__title">
            Forms
            <div class="side-menu__sub-icon">
              <i data-feather="chevron-down" />
            </div>
          </div>
        </a>
        <ul class="">
          <li>
            <a href="side-menu-light-regular-form.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Regular Form</div>
            </a>
          </li>
          <li>
            <a href="side-menu-light-datepicker.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Datepicker</div>
            </a>
          </li>
          <li>
            <a href="side-menu-light-tail-select.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Tail Select</div>
            </a>
          </li>
          <li>
            <a href="side-menu-light-file-upload.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">File Upload</div>
            </a>
          </li>
          <li>
            <a href="side-menu-light-wysiwyg-editor.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Wysiwyg Editor</div>
            </a>
          </li>
          <li>
            <a href="side-menu-light-validation.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Validation</div>
            </a>
          </li>
        </ul>
      </li>
      <li>
        <a href="javascript:;" class="side-menu">
          <div class="side-menu__icon"><i data-feather="hard-drive" /></div>
          <div class="side-menu__title">
            Widgets
            <div class="side-menu__sub-icon">
              <i data-feather="chevron-down" />
            </div>
          </div>
        </a>
        <ul class="">
          <li>
            <a href="side-menu-light-chart.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Chart</div>
            </a>
          </li>
          <li>
            <a href="side-menu-light-slider.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Slider</div>
            </a>
          </li>
          <li>
            <a href="side-menu-light-image-zoom.html" class="side-menu">
              <div class="side-menu__icon"><i data-feather="activity" /></div>
              <div class="side-menu__title">Image Zoom</div>
            </a>
          </li>
        </ul>
      </li>
    </ul>
  </nav>
</template>

<script>
// import "../../assets/css/app.css";
export default {
  name: "SideBarAdmin",
  created() {
    this.navigateHomePage();
  },
  methods: {
    checkNavigationDuplicated(path) {
      return this.$router.currentRoute.fullPath !== path;
    },
    navigateHomePage() {
      let userPagePath = "/admin/users";
      if (this.checkNavigationDuplicated(userPagePath)) {
        this.$router.push({ path: userPagePath });
      }
    },
    navigateRoomPage() {
      let roomPagePath = "/admin/rooms";
      if (this.checkNavigationDuplicated(roomPagePath)) {
        this.$router.push({ path: roomPagePath });
      }
    },
  },
};
</script>

<style src="../../assets/css/app.css" scoped>
</style>