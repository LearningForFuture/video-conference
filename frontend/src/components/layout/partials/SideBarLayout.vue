<template>
<nav class="navigation">
    <div class="nav-group">
        <ul>
            <li v-for="(item, index) in navItems" :key="index" @click=" currentActive = index">
                <router-link tag="a" :to="{path:item.to}" :class="[{ notifiy_badge : item.isNotify}, {active: index === currentActive}]">
                    <i :class=item.icon ></i>
                    <span>{{ item.text }}</span>
                </router-link>
            </li>
        </ul>
    </div>
</nav>
</template>

<script>
export default {
    name: 'SideBarLayout',
    data() {
        return {
            currentActive: 1,
            navItems: [
                {text: "Activity", icon:"ti-bell", to:"/activity", isActive: false, isNotify: true},
                {text: "Teams", icon:"fa fa-users", to:"/teams", isActive: true, isNotify: false},
                {text: "Files", icon:"ti-file", to:"/files", isActive: false, isNotify: false},
                {text: "Helps", icon:"ti-help-alt", to:"/helps", isActive: false, isNotify: false},
                {text: "Cài đặt", icon:"ti-settings", to:"/setting", isActive: false, isNotify: false},
            ]
        }
    },
}
</script>

<style scoped>
nav.navigation {
    background: #202020;
    height: 100vh - 40px;
    height: calc(var(--vh, 1vh) * 100 - 40px);
}
</style>
