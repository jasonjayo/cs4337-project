<template>
  <h1>Dashboard</h1>
  Hello, {{ first_name }}!
  <h2>Teams</h2>
  <div v-for="team in teams">
    {{ team.teamName }}
  </div>

  <h2>Players</h2>
  <div v-for="player in players">
    {{ player.playerName }}
  </div>
</template>

<script>
import { jwtDecode } from "jwt-decode";

export default {
  data() {
    return {
      teams: [],
      players: [],
      first_name: "",
    };
  },
  mounted() {
    const token = localStorage.getItem("jwtToken");

    const decodedToken = jwtDecode(token);
    const first_name = decodedToken.first_name;
    this.first_name = first_name;

    fetch("http://localhost:8080/api/teams", {
      headers: {
        Authorization: "Bearer " + token,
      },
    })
      .then((res) => res.json())
      .then((teams) => (this.teams = teams));

    fetch("http://localhost:8080/api/players", {
      headers: {
        Authorization: "Bearer " + token,
      },
    })
      .then((res) => res.json())
      .then((players) => (this.players = players));
  },
};
</script>
