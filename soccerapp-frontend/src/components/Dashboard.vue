<template>
  <h1>Dashboard</h1>
  Hello, {{ first_name }}! Welcome to Soccer App.
  <img :src="profilePic" id="profile-pic" />

  <h2>My Teams</h2>
  <p>Here's a list of the teams you've joined.</p>
  <ul>
    <li v-for="team in myTeams">{{ team.teamName }}</li>
  </ul>

  <h2>My Events</h2>
  <p>Here's a list of the events for your teams.</p>

  <ul>
    <li v-for="event in myEvents">{{ event.description }}</li>
  </ul>

  <h2>Teams</h2>
  <p>Here's a list of the teams using Soccer App.</p>
  <div id="teams">
    <div class="team" v-for="team in teams">
      <div>{{ team.teamName }}</div>
      <button :data-team-id="team.teamId" @click="joinTeam">Join</button>
    </div>
  </div>

  <h2>Players</h2>
  <p>Here's a list of the players signed up to Soccer App.</p>
  <ul>
    <li v-for="player in players">{{ player.playerName }}</li>
  </ul>
</template>

<script>
import { jwtDecode } from "jwt-decode";

export default {
  data() {
    return {
      teams: [],
      myTeams: [],
      myEvents: [],
      players: [],
      first_name: "",
      id: null,
      token: null,
      profilePic: null,
    };
  },
  mounted() {
    this.token = localStorage.getItem("jwtToken");

    const profilePic = localStorage.getItem("profilePic");
    this.profilePic = profilePic;

    const decodedToken = jwtDecode(this.token);
    const first_name = decodedToken.first_name;
    this.first_name = first_name;
    this.id = decodedToken.id;

    fetch("http://localhost:8080/api/teams", {
      headers: {
        Authorization: "Bearer " + this.token,
      },
    })
      .then((res) => res.json())
      .then((teams) => (this.teams = teams));

    fetch("http://localhost:8080/api/players", {
      headers: {
        Authorization: "Bearer " + this.token,
      },
    })
      .then((res) => res.json())
      .then((players) => (this.players = players));
    this.getMyTeams();
  },
  methods: {
    joinTeam(e) {
      const team_id = e.target.getAttribute("data-team-id");
      let pin = prompt("Enter team PIN to join:");

      fetch(`http://localhost:8080/api/players/${this.id}/teams/${team_id}`, {
        headers: {
          Authorization: "Bearer " + this.token,
          "Content-Type": "application/json",
        },
        method: "POST",
        body: JSON.stringify({ pin }),
      }).then(() => {
        this.getMyTeams();
      });
    },
    getMyTeams() {
      fetch(`http://localhost:8080/api/players/${this.id}`, {
        headers: {
          Authorization: "Bearer " + this.token,
        },
      })
        .then((res) => res.json())
        .then((profile) => {
          this.myTeams = profile.teams;
          this.getMyEvents();
        });
    },
    getMyEvents() {
      this.myEvents = [];
      this.myTeams.forEach((team) => {
        fetch(`http://localhost:8080/api/events/team/${team.teamId}`, {
          headers: {
            Authorization: "Bearer " + this.token,
          },
        })
          .then((res) => res.json())
          .then((events) => {
            this.myEvents.push(...events);
          });
      });
    },
  },
};
</script>

<style lang="scss">
#teams {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  gap: 0.5em;
  margin: 1em 0;
}
.team {
  display: flex;
  flex-grow: 1;
  max-width: 400px;
  justify-content: space-between;
  button {
    align-self: flex-end;
    text-transform: uppercase;
    appearance: none;
    background-color: rgb(104, 166, 221);
    border: 0;
    padding: 0.4em;
    color: inherit;
    font-weight: bold;
    margin-left: 2em;
    cursor: pointer;
  }
}
#profile-pic {
  vertical-align: middle;
  margin-left: 5em;
}
</style>
