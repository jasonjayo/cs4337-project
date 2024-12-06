<template>
  <div id="homepage">
    <!-- hero section -->
    <section class="hero">
      <div class="hero-content">
        <h1>Soccer App</h1>
        <p>Inspiration, Passion, Team Spirit - Join us and run together</p>
        <button class="join-button" @click="loginWithGoogle">
          Sign in with Google
        </button>
      </div>
    </section>

    <!-- footer -->
    <footer>
      <div class="contact-info">
        <p>Phone: +86 123-456-7890</p>
        <p>Email: contact@soccerapp.ie</p>
        <div class="social-links">
          <a href="#"><i class="fab fa-facebook-f"></i></a>
          <a href="#"><i class="fab fa-twitter"></i></a>
          <a href="#"><i class="fab fa-instagram"></i></a>
        </div>
      </div>
      ⚽
    </footer>
  </div>
</template>

<script>
export default {
  name: "Homepage",
  data() {
    return {
      baseUrl: window.location.hostname,
    };
  },
  methods: {
    loginWithGoogle() {
      window.location.href = `http://${this.baseUrl}:8080/oauth2/authorization/google`;
    },
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html,
body {
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  overflow-x: hidden;
  font-family: Arial, sans-serif;
}

#homepage {
  max-width: 1440px;
  width: 100%;
  height: 100%;
  margin: 0 auto;
  padding: 0 1rem;
  background-color: #f0f0f0;
  color: #333;
}

.hero {
  position: relative;
  background-image: url("@/assets/soccer-background.jpg");
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
  overflow: hidden;
  max-width: 1200px;
  height: 60vh;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  text-align: center;

  margin: 0 auto;
}

.hero::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  z-index: 1;
}

.hero-content {
  position: relative;
  z-index: 2;
  width: 100%;
  padding: 1rem;
  max-width: 800px;
  margin: 0 auto;
}

.hero-content h1 {
  font-size: 3rem;
  margin-bottom: 1rem;
  line-height: 1.2;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.7);
}

.hero-content p {
  font-size: 1.25rem;
  margin-bottom: 1.5rem;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.7);
}

.join-button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 1rem 2rem;
  cursor: pointer;
  font-size: 1.2rem;
  transition: background-color 0.3s ease, transform 0.3s ease;
  border-radius: 8px;
}

.join-button:hover {
  background-color: #0056b3;
  transform: translateY(-3px);
}

img {
  max-width: 100%;
  height: auto;
}

footer {
  width: 100%;
  background-color: #002b47;
  color: white;
  padding: 2rem;
  text-align: center;
  margin: 0;
}

.social-links {
  margin-top: 1rem;
}

.social-links a {
  color: white;
  margin: 0 0.5rem;
  text-decoration: none;
  transition: color 0.3s ease;
}

.social-links a:hover {
  color: #ffc107;
}

@media (max-width: 768px) {
  .hero-content h1 {
    font-size: 2rem;
  }

  .hero-content p {
    font-size: 1rem;
  }

  .join-button {
    font-size: 1rem;
  }
}

@media (min-width: 769px) {
  .hero {
    height: 70vh;
  }
}
</style>
