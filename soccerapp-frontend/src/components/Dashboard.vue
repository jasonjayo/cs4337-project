<template>
  <h1>Dashboard</h1>
  Hello, {{ first_name }}! Welcome to Soccer App.
  <img :src="profilePic" id="profile-pic" />

  <h2>My Teams</h2>
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

  <h2>My Events</h2>
  <p>Here's a list of your upcoming events.</p>

  <ul class="my-events">
    <li class="event" v-for="event in myEvents" :key="event.event_id">
      <div class="material-symbols-outlined event-icon">event</div>
      <div>
        <h2 class="event-title">{{ event.title }}</h2>
        <div>
          <b>{{ event.teamName }}</b>
        </div>
        <p>{{ event.description }}</p>
        <div>
          {{ formatDateTime(event.eventDate, event.startTime, event.endTime) }}
        </div>
        <div class="attendance-stats">
          <span class="material-symbols-outlined" title="Attending">check</span
          >{{ event.attending }}
          <span class="material-symbols-outlined" title="Not attending"
            >close</span
          >{{ event.notAttending }}
        </div>
      </div>
      <div class="event-buttons">
        <button
          class="attend-btn"
          :data-event-id="event.event_id"
          :data-status="checkMyAttendance(event.event_id)"
          @click="toggleAttendance"
        >
          <div v-if="checkMyAttendance(event.event_id)">
            <span class="material-symbols-outlined">event_available</span
            >Attending
          </div>
          <div v-else>
            <span class="material-symbols-outlined">calendar_add_on</span>Attend
          </div>
        </button>

        <button
          class="delete-btn"
          :data-event-id="event.event_id"
          @click="deleteEvent"
        >
          <span class="material-symbols-outlined">delete</span>Delete
        </button>
      </div>
    </li>
  </ul>

  <h2>Teams</h2>
  <p>Here's a list of the teams using Soccer App that you can join.</p>
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
      availableTeams: [],
      events: [],
      myEvents: [],
      players: [],
      myAttendance: [],
      first_name: "",
      id: null,
      token: null,
      profilePic: null,
      baseUrl: window.location.hostname,
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

    fetch(`http://${this.baseUrl}:8080/api/teams`, {
      headers: {
        Authorization: "Bearer " + this.token,
      },
    })
      .then(this.checkStatusCode)
      .then((res) => res.json())
      .then((teams) => (this.teams = teams));

    fetch(`http://${this.baseUrl}:8080/api/players`, {
      headers: {
        Authorization: "Bearer " + this.token,
      },
    })
      .then(this.checkStatusCode)
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
        .then(this.checkStatusCode)
        .then(() => {
          this.getMyTeams();
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
        .then(this.checkStatusCode)
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
        .then(this.checkStatusCode)
        .then((res) => res.json())
        .then((profile) => {
          this.myTeams = profile.teams;
          this.availableTeams = this.teams.filter(
            (team) =>
              !this.myTeams.some((myTeam) => myTeam.teamId === team.teamId) // only show team player isn't a member of
          );
          this.getMyEvents();
        });
    },
    getMyEvents() {
      this.myEvents = [];
      let myTeamIds = this.myTeams.map((team) => team.teamId);
      fetch(`http://${this.baseUrl}:8080/api/events`, {
        headers: {
          Authorization: "Bearer " + this.token,
        },
      })
        .then(this.checkStatusCode)
        .then((res) => res.json())
        .then((res) => {
          let events = res.events;
          let attendance = res.attendanceStats;
          events.forEach((event, i) => {
            event.teamName = this.teams.find(
              (team) => team.teamId === event.teamId
            ).teamName;
            event.attending = attendance[i].attending;
            event.notAttending = attendance[i].notAttending;
          });
          this.myEvents.push(
            ...events.filter((event) => myTeamIds.includes(event.teamId))
          );
        });
      this.getMyAttendance();
    },
    getMyAttendance() {
      fetch(`http://${this.baseUrl}:8080/api/players/${this.id}/events`, {
        headers: {
          Authorization: "Bearer " + this.token,
        },
      })
        .then(this.checkStatusCode)
        .then((res) => res.json())
        .then((myAttendance) => (this.myAttendance = myAttendance));
    },
    formatDateTime(dateStr, startTime, endTime) {
      if ((dateStr != null) & (startTime != null) & (endTime != null)) {
        const startDateTime = new Date(`${dateStr}T${startTime}`);
        const endDateTime = new Date(`${dateStr}T${endTime}`);

        const timeFormat = {
          hour: "2-digit",
          minute: "2-digit",
          hour12: false,
        };

        const dateTimeFormatter = new Intl.DateTimeFormat("en-IE", {
          year: "numeric",
          month: "long",
          day: "numeric",
          ...timeFormat,
        });
        const timeFormatter = new Intl.DateTimeFormat("en-IE", {
          ...timeFormat,
        });

        return (
          dateTimeFormatter.format(startDateTime) +
          " until " +
          timeFormatter.format(endDateTime)
        );
      } else {
        return "No date/time info. provided by organiser.";
      }
    },
    checkStatusCode(res) {
      if (res.status === 401) {
        window.location.href = `http://${this.baseUrl}:8080/oauth2/authorization/google`;
      } else {
        return res;
      }
    },
    toggleAttendance(e) {
      const eventId = e.currentTarget.getAttribute("data-event-id"),
        current_status =
          e.currentTarget.getAttribute("data-status") === "true" ? true : false;
      fetch(
        `http://${this.baseUrl}:8080/api/events/${eventId}/attendance/${
          this.id
        }?status=${!current_status}`,
        {
          headers: {
            Authorization: "Bearer " + this.token,
          },
          method: "POST",
        }
      ).then(() => this.getMyEvents());
    },
    checkMyAttendance(eventId) {
      if (eventId in this.myAttendance) {
        return this.myAttendance[eventId];
      }
      return false;
    },
    deleteEvent(e) {
      const eventId = e.currentTarget.getAttribute("data-event-id");
      fetch(`http://${this.baseUrl}:8080/api/events/${eventId}`, {
        headers: {
          Authorization: "Bearer " + this.token,
        },
        method: "DELETE",
      }).then(() => this.getMyEvents());
    },
  },
};
</script>

<style lang="scss">
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
  max-width: 400px;
  justify-content: space-between;
}
.team button,
.attend-btn,
.delete-btn {
  align-self: flex-end;
  text-transform: uppercase;
  appearance: none;
  border: 0;
  padding: 0.4em;
  font-weight: bold;
  margin-left: 2em;
  cursor: pointer;
}
.attend-btn div {
  font-weight: bold;
}
.team button {
  color: #fff;
  background-color: #4fc3f7;
}
.attend-btn,
.delete-btn {
  background: #686868;
  // border: 0.2em solid;
  padding: 0.75em 1em;
  color: inherit;
  margin: 0 0.5em;
}
#profile-pic {
  vertical-align: middle;
  margin-left: 5em;
}
.my-events {
  list-style-type: none;
  padding: 0;
  margin: 1em 0;
}
.event {
  padding: 1.5em;
  background: grey;
  display: flex;
  margin: 1.5em 0;
  gap: 2em;
  align-items: center;
  .event-icon {
    font-size: 3.5em;
  }
}
.join-team-btn,
.leave-team-btn,
.attend-btn > div,
.delete-btn {
  display: flex;
  align-items: center;
  gap: 0 0.75em;
}
.attendance-stats .material-symbols-outlined {
  vertical-align: middle;
  margin: 0 0.1em;
}
.attendance-stats {
  margin-top: 0.75em;
  cursor: default;
}
.event-buttons {
  display: flex;
}
</style>
