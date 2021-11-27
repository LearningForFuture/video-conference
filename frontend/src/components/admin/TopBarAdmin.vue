<template>
  <div class="top-bar">
    <!-- BEGIN: Breadcrumb -->
    <div class="-intro-x breadcrumb mr-auto hidden sm:flex">
      <a href="">Application</a>
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
        class="feather feather-chevron-right breadcrumb__icon"
      >
        <polyline points="9 18 15 12 9 6" />
      </svg>
      <a
        href=""
        class="breadcrumb--active"
      >Dashboard</a>
    </div>
    <!-- END: Breadcrumb -->
    <!-- BEGIN: Search -->
    <div class="intro-x relative mr-3 sm:mr-6">
      <div class="search hidden sm:block">
        <input
          type="text"
          class="
            search__input
            form-control
            border-transparent
            placeholder-theme-13
          "
          placeholder="Search..."
        >
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
          class="feather feather-search search__icon dark:text-gray-300"
        >
          <circle
            cx="11"
            cy="11"
            r="8"
          />
          <line
            x1="21"
            y1="21"
            x2="16.65"
            y2="16.65"
          />
        </svg>
      </div>
      <a
        class="notification sm:hidden"
        href=""
      >
        <i
          data-feather="search"
          class="notification__icon dark:text-gray-300"
        />
      </a>
      <div class="search-result">
        <div class="search-result__content">
          <div class="search-result__content__title">
            Pages
          </div>
          <div class="mb-5">
            <a
              href=""
              class="flex items-center"
            >
              <div
                class="
                  w-8
                  h-8
                  bg-theme-18
                  text-theme-9
                  flex
                  items-center
                  justify-center
                  rounded-full
                "
              >
                <i
                  class="w-4 h-4"
                  data-feather="inbox"
                />
              </div>
              <div class="ml-3">Mail Settings</div>
            </a>
            <a
              href=""
              class="flex items-center mt-2"
            >
              <div
                class="
                  w-8
                  h-8
                  bg-theme-17
                  text-theme-11
                  flex
                  items-center
                  justify-center
                  rounded-full
                "
              >
                <i
                  class="w-4 h-4"
                  data-feather="users"
                />
              </div>
              <div class="ml-3">Users & Permissions</div>
            </a>
            <a
              href=""
              class="flex items-center mt-2"
            >
              <div
                class="
                  w-8
                  h-8
                  bg-theme-14
                  text-theme-10
                  flex
                  items-center
                  justify-center
                  rounded-full
                "
              >
                <i
                  class="w-4 h-4"
                  data-feather="credit-card"
                />
              </div>
              <div class="ml-3">Transactions Report</div>
            </a>
          </div>
          <div class="search-result__content__title">
            Users
          </div>
          <div class="mb-5">
            <a
              href=""
              class="flex items-center mt-2"
            >
              <div class="w-8 h-8 image-fit">
                <img
                  alt="Midone Tailwind HTML Admin Template"
                  class="rounded-full"
                  src="@/assets/images/profile-13.jpg"
                >
              </div>
              <div class="ml-3">Robert De Niro</div>
              <div
                class="ml-auto w-48 truncate text-gray-600 text-xs text-right"
              >
                robertdeniro@left4code.com
              </div>
            </a>
            <a
              href=""
              class="flex items-center mt-2"
            >
              <div class="w-8 h-8 image-fit">
                <img
                  alt="Midone Tailwind HTML Admin Template"
                  class="rounded-full"
                  src="@/assets/images/profile-13.jpg"
                >
              </div>
              <div class="ml-3">Kevin Spacey</div>
              <div
                class="ml-auto w-48 truncate text-gray-600 text-xs text-right"
              >
                kevinspacey@left4code.com
              </div>
            </a>
            <a
              href=""
              class="flex items-center mt-2"
            >
              <div class="w-8 h-8 image-fit">
                <img
                  alt="Midone Tailwind HTML Admin Template"
                  class="rounded-full"
                  src="@/assets/images/profile-7.jpg"
                >
              </div>
              <div class="ml-3">Al Pacino</div>
              <div
                class="ml-auto w-48 truncate text-gray-600 text-xs text-right"
              >
                alpacino@left4code.com
              </div>
            </a>
            <a
              href=""
              class="flex items-center mt-2"
            >
              <div class="w-8 h-8 image-fit">
                <img
                  alt="Midone Tailwind HTML Admin Template"
                  class="rounded-full"
                  src="@/assets/images/profile-2.jpg"
                >
              </div>
              <div class="ml-3">Leonardo DiCaprio</div>
              <div
                class="ml-auto w-48 truncate text-gray-600 text-xs text-right"
              >
                leonardodicaprio@left4code.com
              </div>
            </a>
          </div>
          <div class="search-result__content__title">
            Products
          </div>
          <a
            href=""
            class="flex items-center mt-2"
          >
            <div class="w-8 h-8 image-fit">
              <img
                alt="Midone Tailwind HTML Admin Template"
                class="rounded-full"
                src="@/assets/images/preview-15.jpg"
              >
            </div>
            <div class="ml-3">Nike Tanjun</div>
            <div class="ml-auto w-48 truncate text-gray-600 text-xs text-right">
              Sport &amp; Outdoor
            </div>
          </a>
          <a
            href=""
            class="flex items-center mt-2"
          >
            <div class="w-8 h-8 image-fit">
              <img
                alt="Midone Tailwind HTML Admin Template"
                class="rounded-full"
                src="@/assets/images/preview-8.jpg"
              >
            </div>
            <div class="ml-3">Samsung Q90 QLED TV</div>
            <div class="ml-auto w-48 truncate text-gray-600 text-xs text-right">
              Electronic
            </div>
          </a>
          <a
            href=""
            class="flex items-center mt-2"
          >
            <div class="w-8 h-8 image-fit">
              <img
                alt="Midone Tailwind HTML Admin Template"
                class="rounded-full"
                src="@/assets/images/preview-12.jpg"
              >
            </div>
            <div class="ml-3">Nike Air Max 270</div>
            <div class="ml-auto w-48 truncate text-gray-600 text-xs text-right">
              Sport &amp; Outdoor
            </div>
          </a>
          <a
            href=""
            class="flex items-center mt-2"
          >
            <div class="w-8 h-8 image-fit">
              <img
                alt="Midone Tailwind HTML Admin Template"
                class="rounded-full"
                src="@/assets/images/preview-5.jpg"
              >
            </div>
            <div class="ml-3">Apple MacBook Pro 13</div>
            <div class="ml-auto w-48 truncate text-gray-600 text-xs text-right">
              PC &amp; Laptop
            </div>
          </a>
        </div>
      </div>
    </div>
    <!-- END: Search -->
    <!-- BEGIN: Notifications -->
    <div class="intro-x dropdown mr-auto sm:mr-6">
      <div
        class="dropdown-toggle notification notification--bullet cursor-pointer"
        role="button"
        aria-expanded="false"
      >
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
          class="feather feather-bell notification__icon dark:text-gray-300"
        >
          <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9" />
          <path d="M13.73 21a2 2 0 0 1-3.46 0" />
        </svg>
      </div>
      <div class="notification-content pt-2 dropdown-menu">
        <div
          class="
            notification-content__box
            dropdown-menu__content
            box
            dark:bg-dark-6
          "
        >
          <div class="notification-content__title">
            Notifications
          </div>
          <div class="cursor-pointer relative flex items-center">
            <div class="w-12 h-12 flex-none image-fit mr-1">
              <img
                alt="Midone Tailwind HTML Admin Template"
                class="rounded-full"
                src="@/assets/images/profile-13.jpg"
              >
              <div
                class="
                  w-3
                  h-3
                  bg-theme-9
                  absolute
                  right-0
                  bottom-0
                  rounded-full
                  border-2 border-white
                "
              />
            </div>
            <div class="ml-2 overflow-hidden">
              <div class="flex items-center">
                <a
                  href="javascript:;"
                  class="font-medium truncate mr-5"
                >Robert De Niro</a>
                <div class="text-xs text-gray-500 ml-auto whitespace-nowrap">
                  05:09 AM
                </div>
              </div>
              <div class="w-full truncate text-gray-600 mt-0.5">
                Contrary to popular belief, Lorem Ipsum is not simply random
                text. It has roots in a piece of classical Latin literature from
                45 BC, making it over 20
              </div>
            </div>
          </div>
          <div class="cursor-pointer relative flex items-center mt-5">
            <div class="w-12 h-12 flex-none image-fit mr-1">
              <img
                alt="Midone Tailwind HTML Admin Template"
                class="rounded-full"
                src="@/assets/images/profile-13.jpg"
              >
              <div
                class="
                  w-3
                  h-3
                  bg-theme-9
                  absolute
                  right-0
                  bottom-0
                  rounded-full
                  border-2 border-white
                "
              />
            </div>
            <div class="ml-2 overflow-hidden">
              <div class="flex items-center">
                <a
                  href="javascript:;"
                  class="font-medium truncate mr-5"
                >Kevin Spacey</a>
                <div class="text-xs text-gray-500 ml-auto whitespace-nowrap">
                  05:09 AM
                </div>
              </div>
              <div class="w-full truncate text-gray-600 mt-0.5">
                It is a long established fact that a reader will be
                @/assetsracted by the readable content of a page when looking at
                its layout. The point of using Lorem
              </div>
            </div>
          </div>
          <div class="cursor-pointer relative flex items-center mt-5">
            <div class="w-12 h-12 flex-none image-fit mr-1">
              <img
                alt="Midone Tailwind HTML Admin Template"
                class="rounded-full"
                src="@/assets/images/profile-7.jpg"
              >
              <div
                class="
                  w-3
                  h-3
                  bg-theme-9
                  absolute
                  right-0
                  bottom-0
                  rounded-full
                  border-2 border-white
                "
              />
            </div>
            <div class="ml-2 overflow-hidden">
              <div class="flex items-center">
                <a
                  href="javascript:;"
                  class="font-medium truncate mr-5"
                >Al Pacino</a>
                <div class="text-xs text-gray-500 ml-auto whitespace-nowrap">
                  01:10 PM
                </div>
              </div>
              <div class="w-full truncate text-gray-600 mt-0.5">
                Contrary to popular belief, Lorem Ipsum is not simply random
                text. It has roots in a piece of classical Latin literature from
                45 BC, making it over 20
              </div>
            </div>
          </div>
          <div class="cursor-pointer relative flex items-center mt-5">
            <div class="w-12 h-12 flex-none image-fit mr-1">
              <img
                alt="Midone Tailwind HTML Admin Template"
                class="rounded-full"
                src="@/assets/images/profile-2.jpg"
              >
              <div
                class="
                  w-3
                  h-3
                  bg-theme-9
                  absolute
                  right-0
                  bottom-0
                  rounded-full
                  border-2 border-white
                "
              />
            </div>
            <div class="ml-2 overflow-hidden">
              <div class="flex items-center">
                <a
                  href="javascript:;"
                  class="font-medium truncate mr-5"
                >Leonardo DiCaprio</a>
                <div class="text-xs text-gray-500 ml-auto whitespace-nowrap">
                  01:10 PM
                </div>
              </div>
              <div class="w-full truncate text-gray-600 mt-0.5">
                It is a long established fact that a reader will be
                @/assetsracted by the readable content of a page when looking at
                its layout. The point of using Lorem
              </div>
            </div>
          </div>
          <div class="cursor-pointer relative flex items-center mt-5">
            <div class="w-12 h-12 flex-none image-fit mr-1">
              <img
                alt="Midone Tailwind HTML Admin Template"
                class="rounded-full"
                src="@/assets/images/profile-2.jpg"
              >
              <div
                class="
                  w-3
                  h-3
                  bg-theme-9
                  absolute
                  right-0
                  bottom-0
                  rounded-full
                  border-2 border-white
                "
              />
            </div>
            <div class="ml-2 overflow-hidden">
              <div class="flex items-center">
                <a
                  href="javascript:;"
                  class="font-medium truncate mr-5"
                >Edward Norton</a>
                <div class="text-xs text-gray-500 ml-auto whitespace-nowrap">
                  06:05 AM
                </div>
              </div>
              <div class="w-full truncate text-gray-600 mt-0.5">
                There are many variations of passages of Lorem Ipsum available,
                but the majority have suffered alteration in some form, by
                injected humour, or randomi
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- END: Notifications -->
    <!-- BEGIN: Account Menu -->
    <div
      class="intro-x dropdown w-8 h-8"
      @click="isActive = !isActive"
    >
      <div
        class="
          dropdown-toggle
          w-8
          h-8
          rounded-full
          overflow-hidden
          shadow-lg
          image-fit
          zoom-in
        "
        role="button"
        aria-expanded="true"
      >
        <img
          alt="Midone Tailwind HTML Admin Template"
          src="@/assets/images/profile-2.jpg"
        >
      </div>
      <div
        v-if="isActive"
        id="_piiuhevej"
        class="dropdown-menu w-56"
        style="
          width: 224px;
          position: absolute;
          inset: 0px auto auto 0px;
          margin: 0px;
          transform: translate(-192px, 35px);
        "
        data-popper-placement="bottom-end"
        :class="{ show: isActive }"
      >
        <div
          class="
            dropdown-menu__content
            box
            bg-theme-26
            dark:bg-dark-6
            text-white
          "
        >
          <div class="p-4 border-b border-theme-27 dark:border-dark-3">
            <div class="font-medium">
              Morgan Freeman
            </div>
            <div class="text-xs text-theme-28 mt-0.5 dark:text-gray-600">
              Software Engineer
            </div>
          </div>
          <div class="p-2">
            <a
              href=""
              class="
                flex
                items-center
                block
                p-2
                transition
                duration-300
                ease-in-out
                hover:bg-theme-1
                dark:hover:bg-dark-3
                rounded-md
              "
            >
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
                class="feather feather-user w-4 h-4 mr-2"
              >
                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2" />
                <circle
                  cx="12"
                  cy="7"
                  r="4"
                />
              </svg>
              Profile
            </a>
            <a
              href=""
              class="
                flex
                items-center
                block
                p-2
                transition
                duration-300
                ease-in-out
                hover:bg-theme-1
                dark:hover:bg-dark-3
                rounded-md
              "
            >
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
                class="feather feather-edit w-4 h-4 mr-2"
              >
                <path
                  d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"
                />
                <path
                  d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"
                />
              </svg>
              Add Account
            </a>
            <a
              href=""
              class="
                flex
                items-center
                block
                p-2
                transition
                duration-300
                ease-in-out
                hover:bg-theme-1
                dark:hover:bg-dark-3
                rounded-md
              "
            >
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
                class="feather feather-lock w-4 h-4 mr-2"
              >
                <rect
                  x="3"
                  y="11"
                  width="18"
                  height="11"
                  rx="2"
                  ry="2"
                />
                <path d="M7 11V7a5 5 0 0 1 10 0v4" />
              </svg>
              Reset Password
            </a>
            <a
              href=""
              class="
                flex
                items-center
                block
                p-2
                transition
                duration-300
                ease-in-out
                hover:bg-theme-1
                dark:hover:bg-dark-3
                rounded-md
              "
            >
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
                class="feather feather-help-circle w-4 h-4 mr-2"
              >
                <circle
                  cx="12"
                  cy="12"
                  r="10"
                />
                <path d="M9.09 9a3 3 0 0 1 5.83 1c0 2-3 3-3 3" />
                <line
                  x1="12"
                  y1="17"
                  x2="12.01"
                  y2="17"
                />
              </svg>
              Help
            </a>
          </div>
          <div class="p-2 border-t border-theme-27 dark:border-dark-3">
            <a
              href=""
              class="
                flex
                items-center
                block
                p-2
                transition
                duration-300
                ease-in-out
                hover:bg-theme-1
                dark:hover:bg-dark-3
                rounded-md
              "
            >
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
                class="feather feather-toggle-right w-4 h-4 mr-2"
              >
                <rect
                  x="1"
                  y="5"
                  width="22"
                  height="14"
                  rx="7"
                  ry="7"
                />
                <circle
                  cx="16"
                  cy="12"
                  r="3"
                />
              </svg>
              Logout
            </a>
          </div>
        </div>
      </div>
    </div>
    <!-- END: Account Menu -->
  </div>
</template>

<script>
export default {
  name: "TopBarAdmin",
  data() {
    return {
      isActive: false,
    };
  },
};
</script>

<style>
</style>