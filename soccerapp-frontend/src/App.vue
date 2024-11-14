<template>
  <div id="app">
    <!-- 导航栏可以保留在这里 -->
    <!-- <header>
      <nav class="navbar">
        <ul class="nav-links">
          <li><router-link to="/" class="nav-button">Home</router-link></li>
          <li><router-link to="/team" class="nav-button">Team</router-link></li> -->
    <!-- 其他导航链接 -->
    <!-- </ul>
      </nav>
    </header> -->
    <header>
      <nav class="navbar">
        <div class="logo">
          <img
            alt="Club Logo"
            class="logo"
            src="@/assets/logo.png"
            style="width: 100px; height: auto"
          />
          <span>Soccer Club</span>
        </div>
        <ul class="nav-links">
          <li><router-link to="/" class="nav-button">Home</router-link></li>
          <li>
            <router-link to="/dashboard" class="nav-button"
              >Dashboard</router-link
            >
          </li>
          <li>
            <router-link to="/schedule" class="nav-button"
              >Training Schedule</router-link
            >
          </li>
          <li>
            <router-link to="/news" class="nav-button"
              >News & Events</router-link
            >
          </li>
          <li><router-link to="/team" class="nav-button">Team</router-link></li>
          <li>
            <button @click="loginWithGoogle" class="login-button">Login</button>
          </li>
        </ul>
      </nav>
    </header>

    <!-- 用于显示不同路由的内容 -->
    <router-view />
  </div>
</template>

<script>
export default {
  name: "App",
};
</script>

<style scoped>
/* 可以在这里加入你的样式 */
.navbar {
  width: 100%;
  max-width: 1440px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 1.5rem;
  background-color: #004d7a;
  color: white;
  position: sticky;
  top: 0;
  z-index: 10;
}

.navbar .logo {
  display: flex;
  align-items: center;
}

.nav-links {
  list-style: none;
  display: flex;
  align-items: center;
  gap: 1rem;
  margin: 0;
  padding: 0;
  flex-wrap: wrap;
}

.nav-links li {
  display: inline;
}

.nav-button {
  background-color: #007bff;
  color: white;
  padding: 0.5rem 1rem;
  font-size: 0.9rem;
  border: none;
  border-radius: 5px;
  text-decoration: none;
  font-weight: bold;
  transition: background-color 0.3s ease, transform 0.3s ease;
  cursor: pointer;
  display: inline-flex;
  align-items: center;
  justify-content: center;
}

.nav-button:hover {
  background-color: #0056b3;
  transform: translateY(-2px);
}

.navbar .logo img {
  width: 50px;
  height: auto;
  margin-right: 10px;
}
.login-button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.login-button:hover {
  background-color: #0056b3;
  transform: translateY(-2px);
}
</style>
