<template>
  <div class="redirecting">Redirecting...</div>
</template>

<script>
export default {
  name: "OAuth2RedirectHandler",
  mounted() {
    const searchParams = new URLSearchParams(window.location.search);
    const token = searchParams.get("token");

    if (token) {
      // Store the token in localStorage for use in API calls
      localStorage.setItem("jwtToken", token);

      // Redirect to a protected page (e.g., dashboard)
      this.$router.push("/team");
    } else {
      // Redirect to login if there's an error or no token
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
