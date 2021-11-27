<template>
  <div class="content">
    <top-bar-admin />
    <h2 class="intro-y text-lg font-medium mt-10">
      Quản lý user
    </h2>
    <div class="grid grid-cols-12 gap-6 mt-5">
      <div
        class="
          intro-y
          col-span-12
          flex flex-wrap
          sm:flex-nowrap
          items-center
          mt-2
        "
      >
        <button
          class="btn btn-primary shadow-md mr-2"
          @click="showModalCreateUser = true"
        >
          Thêm user
        </button>
        <div class="dropdown">
          <button
            class="
              dropdown-toggle
              btn
              px-2
              box
              text-gray-700
              dark:text-gray-300
            "
            aria-expanded="false"
          >
            <span class="w-5 h-5 flex items-center justify-center">
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
                class="feather feather-plus w-4 h-4"
              >
                <line
                  x1="12"
                  y1="5"
                  x2="12"
                  y2="19"
                />
                <line
                  x1="5"
                  y1="12"
                  x2="19"
                  y2="12"
                />
              </svg>
            </span>
          </button>
          <div class="dropdown-menu w-40">
            <div class="dropdown-menu__content box dark:bg-dark-1 p-2">
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
                  bg-white
                  dark:bg-dark-1
                  hover:bg-gray-200
                  dark:hover:bg-dark-2
                  rounded-md
                "
              >
                <i
                  data-feather="printer"
                  class="w-4 h-4 mr-2"
                /> Print
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
                  bg-white
                  dark:bg-dark-1
                  hover:bg-gray-200
                  dark:hover:bg-dark-2
                  rounded-md
                "
              >
                <i
                  data-feather="file-text"
                  class="w-4 h-4 mr-2"
                /> Export to
                Excel
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
                  bg-white
                  dark:bg-dark-1
                  hover:bg-gray-200
                  dark:hover:bg-dark-2
                  rounded-md
                "
              >
                <i
                  data-feather="file-text"
                  class="w-4 h-4 mr-2"
                /> Export to
                PDF
              </a>
            </div>
          </div>
        </div>
        <div class="hidden md:block mx-auto text-gray-600">
          Showing 1 to 10 of 150 entries
        </div>
        <div class="w-full sm:w-auto mt-3 sm:mt-0 sm:ml-auto md:ml-0">
          <div class="w-56 relative text-gray-700 dark:text-gray-300">
            <input
              v-model="searchText"
              type="text"
              class="form-control w-56 box pr-10 placeholder-theme-13"
              placeholder="Search..."
            >
            <button
              class="btn-search"
              @click="search"
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
                class="
                  feather feather-search
                  w-4
                  h-4
                  absolute
                  my-auto
                  inset-y-0
                  mr-3
                  right-0
                "
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
            </button>
          </div>
        </div>
      </div>
      <!-- BEGIN: Data List -->
      <div class="intro-y col-span-12 overflow-auto lg:overflow-visible">
        <table class="table table-report -mt-2">
          <thead>
            <tr>
              <th class="whitespace-nowrap">
                STT
              </th>
              <th class="whitespace-nowrap">
                FULLNAME
              </th>
              <th class="whitespace-nowrap">
                USERNAME
              </th>
              <th class="whitespace-nowrap">
                EMAIL
              </th>
              <th class="text-center whitespace-nowrap">
                STATUS
              </th>
              <th class="text-center whitespace-nowrap">
                ROLE
              </th>
              <th class="text-center whitespace-nowrap">
                ACTIONS
              </th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="(user, index) in getUserPage.users"
              :key="user.userId"
              class="intro-x"
            >
              <td class="">
                {{
                  ++index +
                    getUserPage.totalItems * (getUserPage.currentPage - 1)
                }}
              </td>
              <td>{{ user.fullName }}</td>
              <td>{{ user.username }}</td>
              <td>{{ user.email }}</td>
              <td
                v-if="user.enabled"
                class="w-40"
              >
                <div class="flex items-center justify-center text-theme-9">
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
                    class="feather feather-check-square w-4 h-4 mr-2"
                  >
                    <polyline points="9 11 12 14 22 4" />
                    <path
                      d="M21 12v7a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11"
                    /></svg>Active
                </div>
              </td>
              <td
                v-else
                class="w-40"
              >
                <div class="flex items-center justify-center text-theme-6">
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
                    class="feather feather-check-square w-4 h-4 mr-2"
                  >
                    <polyline points="9 11 12 14 22 4" />
                    <path
                      d="M21 12v7a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11"
                    /></svg>Inactive
                </div>
              </td>
              <td class="text-center">
                ADMIN
              </td>
              <td class="table-report__action w-56">
                <div class="flex justify-center items-center">
                  <a
                    class="flex items-center mr-3"
                    href="javascript:;"
                    @click="
                      (userUpdateId = user.userId), (showModalUpdateUser = true)
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
                      class="feather feather-check-square w-4 h-4 mr-1"
                    >
                      <polyline points="9 11 12 14 22 4" />
                      <path
                        d="M21 12v7a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11"
                      /></svg>Edit
                  </a>
                  <a
                    class="flex items-center text-theme-6"
                    href="javascript:;"
                    @click="
                      (userDeleteId = user.userId), (showModalDeleteUser = true)
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
                      class="feather feather-trash-2 w-4 h-4 mr-1"
                    >
                      <polyline points="3 6 5 6 21 6" />
                      <path
                        d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"
                      />
                      <line
                        x1="10"
                        y1="11"
                        x2="10"
                        y2="17"
                      />
                      <line
                        x1="14"
                        y1="11"
                        x2="14"
                        y2="17"
                      /></svg>Delete
                  </a>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <!-- END: Data List -->
      <!-- BEGIN: Pagination -->
      <modal-create-user-admin
        v-if="showModalCreateUser"
        @close="showModalCreateUser = false"
      />
      <modal-update-user-admin
        v-if="showModalUpdateUser"
        @close="showModalUpdateUser = false"
      />
      <modal-delete-user-admin
        v-if="showModalDeleteUser"
        :user-id="userDeleteId"
        @close="showModalDeleteUser = false"
      />

      <pagination
        :total-pages="getUserPage.totalPages"
        :per-page="getUserPage.totalItems"
        :current-page="getUserPage.currentPage"
        @pagechanged="onPageChange"
      />
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import Pagination from "./Pagination.vue";
import ModalCreateUserAdmin from "./ModalCreateUserAdmin.vue";
import ModalUpdateUserAdmin from "./ModalUpdateUserAdmin.vue";
import ModalDeleteUserAdmin from "./ModalDeleteUserAdmin.vue";
import TopBarAdmin from "./TopBarAdmin.vue";

export default {
  name: "Table",
  components: {
    pagination: Pagination,
    ModalCreateUserAdmin,
    ModalUpdateUserAdmin,
    ModalDeleteUserAdmin,
    TopBarAdmin,
  },
  data() {
    return {
      searchText: "",
      showModalCreateUser: false,
      showModalUpdateUser: false,
      showModalDeleteUser: false,
      userUpdateId: 0,
      userDeleteId: 0,
    };
  },
  provide: function () {
    const appData = {};
    Object.defineProperty(appData, "userUpdateId", {
      enumerable: true,
      get: () => this.userUpdateId,
    });
    return {
      appData,
    };
  },
  computed: {
    ...mapGetters("users", ["getUserPage"]),
  },
  methods: {
    ...mapActions("users", ["getAllUsers"]),

    async getUsersAction(pageUser) {
      await this.getAllUsers(pageUser);
    },
    onPageChange(page) {
      this.getUserPage.currentPage = page;
      const pageUser = {
        page: this.getUserPage.currentPage || 1,
        size: 10,
        sort: "",
        keyword: this.searchText,
      };
      this.getUsersAction(pageUser);
    },
    search() {
      const pageUser = {
        page: 1,
        size: 10,
        sort: "",
        keyword: this.searchText,
      };
      this.getUsersAction(pageUser);
    },
  },
  created() {
    this.getUsersAction();
  },
};
</script>

<style>
</style>