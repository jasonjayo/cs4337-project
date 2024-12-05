<template>
  <div id="welcome">
    <h1>⚽ Welcome to Soccer App!</h1>
    <p>Join teams, create events and log attendance</p>
  </div>
  <div id="dashboard">
    <div>
      <h1>Dashboard</h1>
      <section id="top">
        <div id="my-info">
          Hello, {{ first_name }}! Welcome to Soccer App.
          <p>Your email: {{ email }}</p>
          <p>Your user ID: {{ id }}</p>
          <button id="logout-btn" @click="logout">Log out</button>
        </div>
        <div id="profile-pic">
          <img :src="profilePic" />
        </div>
      </section>

      <section>
        <h2><span class="material-symbols-outlined">group</span>My Teams</h2>
        <p>Here's a list of the teams you've joined.</p>
        <div id="my-teams">
          <div class="team" v-for="team in myTeams">
            {{ team.teamName }}
            <button
              :data-team-id="team.teamId"
              class="leave-team-btn"
              @click="leaveTeam"
            >
              <span class="material-symbols-outlined">person_remove</span>Leave
            </button>
          </div>
        </div>
      </section>

      <section>
        <h2><span class="material-symbols-outlined">groups</span>Teams</h2>
        <p>Here's a list of the teams using Soccer App that you can join.</p>
        <p>
          After joining a team, you'll be able to see their details of their
          events!
        </p>
        <div id="teams">
          <div class="team" v-for="team in availableTeams">
            <div>{{ team.teamName }}</div>
            <button
              :data-team-id="team.teamId"
              class="join-team-btn"
              @click="joinTeam"
            >
              <span class="material-symbols-outlined">person_add</span>Join
            </button>
          </div>
        </div>
      </section>

      <section>
        <h2><span class="material-symbols-outlined">person</span>Players</h2>
        <p>Here's a list of the players signed up to Soccer App.</p>
        <ul>
          <li v-for="player in players">{{ player.playerName }}</li>
        </ul>
      </section>
    </div>
  </div>
</template>

<script>
import { checkStatusCode } from "../utils";
import { jwtDecode } from "jwt-decode";

export default {
  data() {
    return {
      teams: [],
      myTeams: [],
      availableTeams: [],
      players: [],
      myAttendance: [],
      first_name: "",
      id: null,
      token: null,
      profilePic: null,
      email: "",
      baseUrl: window.location.hostname,
    };
  },
  mounted() {
    this.token = localStorage.getItem("jwtToken");

    const profilePic = localStorage.getItem("profilePic");
    this.profilePic = profilePic;

    const decodedToken = jwtDecode(this.token);
    const first_name = decodedToken.first_name;
    this.email = decodedToken.sub;
    this.first_name = first_name;
    this.id = decodedToken.id;

    fetch(`http://${this.baseUrl}:8080/api/teams`, {
      headers: {
        Authorization: "Bearer " + this.token,
      },
    })
      .then(checkStatusCode)
      .then((res) => res.json())
      .then((teams) => (this.teams = teams));

    fetch(`http://${this.baseUrl}:8080/api/players`, {
      headers: {
        Authorization: "Bearer " + this.token,
      },
    })
      .then(checkStatusCode)
      .then((res) => res.json())
      .then((players) => (this.players = players));
    this.getMyTeams();
  },
  methods: {
    joinTeam(e) {
      const team_id = e.target.getAttribute("data-team-id");
      let pin = prompt("Enter team PIN to join:");

      fetch(
        `http://${this.baseUrl}:8080/api/players/${this.id}/teams/${team_id}`,
        {
          headers: {
            Authorization: "Bearer " + this.token,
            "Content-Type": "application/json",
          },
          method: "POST",
          body: JSON.stringify({ pin }),
        }
      )
        .then(checkStatusCode)
        .then((res) => res.text())
        .then((res) => {
          if (res == "true") {
            this.getMyTeams();
            alert("Joined team successfully!");
          } else {
            alert("Failed to join team. Check PIN.");
          }
        });
    },
    leaveTeam(e) {
      const team_id = e.target.getAttribute("data-team-id");

      fetch(
        `http://${this.baseUrl}:8080/api/players/${this.id}/teams/${team_id}`,
        {
          headers: {
            Authorization: "Bearer " + this.token,
            "Content-Type": "application/json",
          },
          method: "DELETE",
        }
      )
        .then(checkStatusCode)
        .then(() => {
          this.getMyTeams();
        });
    },
    getMyTeams() {
      fetch(`http://${this.baseUrl}:8080/api/players/${this.id}`, {
        headers: {
          Authorization: "Bearer " + this.token,
        },
      })
        .then(checkStatusCode)
        .then((res) => res.json())
        .then((profile) => {
          this.myTeams = profile.teams;
          this.availableTeams = this.teams.filter(
            (team) =>
              !this.myTeams.some((myTeam) => myTeam.teamId === team.teamId) // only show team player isn't a member of
          );
        });
    },
    logout() {
      localStorage.removeItem("jwtToken");
      window.location = "/";
    },
  },
};
</script>

<style lang="scss">
#welcome {
  padding: 10em 0;
  text-align: center;
  color: #fff;
  margin-top: 2em;
  border-radius: 4em;
  position: relative;
  overflow: hidden;
  &::after {
    content: "";
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: url("../assets/soccer-background.jpg");
    filter: blur(10px);
  }
  &::before {
    content: "";
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    z-index: 1;
  }
  h1,
  p {
    z-index: 2;
    position: relative;
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.7);
  }
  h1 {
    font-size: 4em;
  }
  p {
    font-size: 2em;
  }
}
#dashboard {
  display: flex;
  margin-top: 3em;
  justify-content: center;
  & div {
    width: 550px;
  }
}
#top {
  display: flex;
}
#teams,
#my-teams {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  gap: 0.5em;
  margin: 1em 0;
}
.team {
  display: flex;
  flex-grow: 1;
  justify-content: space-between;
}
.team button,
.attend-btn,
.delete-btn,
.edit-btn {
  align-self: flex-end;
  text-transform: uppercase;
  appearance: none;
  border: 0;
  padding: 0.4em;
  font-weight: bold;
  margin-left: 2em;
  cursor: pointer;
}

.team button {
  color: #fff;
  background-color: #4fc3f7;
}

#profile-pic {
  margin-left: 5em;
  flex-grow: 1;
  display: flex;
  justify-content: flex-end;
  height: 100%;
}

.join-team-btn,
.leave-team-btn,
.attend-btn > div,
.delete-btn,
.edit-btn {
  display: flex;
  align-items: center;
  gap: 0 0.75em;
}
h2 {
  display: flex;
  align-items: center;
  padding: 1em 0;
  .material-symbols-outlined {
    margin-right: 0.5em;
    font-size: 1.5em;
  }
}
#logout-btn {
  appearance: none;
  border: none;
  padding: 0.25em 0.5em;
  margin-top: 1em;
  cursor: pointer;
}
</style>
