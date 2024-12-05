<template>
  <h1>Create New Event</h1>
  <EventForm />
</template>
<script>
export default {
  name: "CreateEvent",
  methods: {},
  data() {
    return {
      baseUrl: window.location.hostname,
      token: localStorage.getItem("jwtToken"),
    };
  },
};
</script>
<style lang="scss" scoped></style>
