<!-- final part of the auth flow, takes the JWT token from backend and stores it in local storage -->
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
    // we also take the profile pic URL and store it in local storage

    if (token) {
      localStorage.setItem("jwtToken", token);
      localStorage.setItem("profilePic", profilePic);
      this.$router.push("/dashboard"); // redirect to dashboard
    } else {
      this.$router.push("/login"); // redirect to login if no token provided
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
