<template>
  <div
    v-show="!isJoined"
    class="row-team"
  >
    <teams-list-groups-items
      v-for="(group, idGroups) in listGroups"
      :key="idGroups"
      :name-group="group.nameGroups"
    />
  </div>
</template>

<script>
import TeamsListGroupsItems from './TeamsListGroupsItems.vue'

export default {
    name: 'Teamslistgroups',

    components: {
        TeamsListGroupsItems,
    },

    props: {
        isJoined: {
            type: Boolean,
            required: true
        }
    },

    data() {
        return {
            listGroups: [
                {idGroups: 1, nameGroups: 'Group Xom Tro'},
                {idGroups: 2, nameGroups: 'Group Xom Tro'},
                {idGroups: 3, nameGroups: 'Group Xom Tro'},
                {idGroups: 4, nameGroups: 'Group Xom Tro'},
                {idGroups: 5, nameGroups: 'Group Xom Tro'},
                {idGroups: 6, nameGroups: 'Group Xom Tro'},
                {idGroups: 7, nameGroups: 'Group Xom Tro'},
                {idGroups: 8, nameGroups: 'Group Xom Tro'},
                {idGroups: 9, nameGroups: 'Group Xom Tro'},
            ]
        };
    },

    mounted() {
        
    },

    methods: {
        
    },
};
</script>

<style lang="scss" scoped>

</style>