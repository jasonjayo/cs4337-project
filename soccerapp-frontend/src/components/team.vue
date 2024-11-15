<template>
  <div id="team-page">
    <!-- Team Selection -->
    <header>
      <h1 class="team-title">Select a Team</h1>
      <select v-model="selectedTeam" @change="onTeamChange" class="team-select">
        <option v-for="(team, index) in teams" :key="index" :value="team.name">
          {{ team.name }}
        </option>
      </select>
    </header>

    <!-- Players List -->
    <section v-if="selectedTeam" class="team-players">
      <h2>{{ selectedTeam }} Players</h2>
      <div
        v-for="(player, index) in filteredPlayers"
        :key="index"
        class="team-member-card"
      >
        <h3>{{ player.name }}</h3>
        <p>{{ player.position }}</p>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  name: "TeamPage",
  data() {
    return {
      teams: [
        {
          name: "Team A",
          players: [
            { name: "Player 1", position: "Forward" },
            { name: "Player 2", position: "Midfielder" },
          ],
        },
        {
          name: "Team B",
          players: [
            { name: "Player 3", position: "Defender" },
            { name: "Player 4", position: "Goalkeeper" },
          ],
        },
      ],
      selectedTeam: null,
    };
  },
  computed: {
    filteredPlayers() {
      const team = this.teams.find((team) => team.name === this.selectedTeam);
      return team ? team.players : [];
    },
  },
  methods: {
    onTeamChange() {
      // Handle team selection change if needed
    },
  },
};
</script>

<style scoped>
#team-page {
  max-width: 1440px;
  width: 100%;
  margin: 2rem auto;
  padding: 1rem;
  background-color: #f9f9f9;
  text-align: center;
}

.team-title {
  font-size: 2rem;
  color: #004d7a;
  margin-bottom: 1rem;
}

.team-select {
  padding: 0.5rem;
  font-size: 1rem;
  margin-bottom: 2rem;
}

.team-players {
  margin-top: 2rem;
}

.team-member-card {
  width: 250px;
  background: white;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 1rem;
  text-align: center;
  margin: 1rem auto;
}

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
