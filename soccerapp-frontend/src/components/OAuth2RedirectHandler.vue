<template>
  <div class="redirecting">Redirecting...</div>
</template>

<script>
export default {
  name: "OAuth2RedirectHandler",
  mounted() {
    const searchParams = new URLSearchParams(window.location.search);
    const token = searchParams.get("token"),
      profilePic = searchParams.get("profile_pic");

    if (token) {
      localStorage.setItem("jwtToken", token);
      localStorage.setItem("profilePic", profilePic);
      this.$router.push("/dashboard");
    } else {
      this.$router.push("/login");
    }
  },
};
</script>

<style scoped>
.redirecting {
  text-align: center;
  margin-top: 50px;
  font-size: 20px;
}
</style>
