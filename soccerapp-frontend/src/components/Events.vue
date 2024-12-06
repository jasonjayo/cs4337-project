<!-- page listing out events for player based on their teams -->
<template>
  <h1>My Events</h1>
  <p>Here's a list of your upcoming events!</p>
  <p>You can mark your attendance and see attendance stats.</p>

  <ul class="my-events">
    <li class="event" v-for="event in myEvents" :key="event.event_id">
      <div class="material-symbols-outlined event-icon">event</div>
      <div class="event-details">
        <h2 class="event-title">{{ event.title }}</h2>
        <div>
          <b>{{ event.teamName }}</b>
        </div>
        <p v-if="event.description">
          <span class="material-symbols-outlined">info</span
          >{{ event.description }}
        </p>
        <p v-if="event.eventType">
          <span class="material-symbols-outlined">description</span
          >{{ event.eventType }}
        </p>
        <p v-if="event.location">
          <span class="material-symbols-outlined">location_on</span
          >{{ event.location }}
        </p>
        <p>
          <span class="material-symbols-outlined">schedule</span>
          {{ formatDateTime(event.eventDate, event.startTime, event.endTime) }}
        </p>
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

        <button
          class="edit-btn"
          :data-event-id="event.event_id"
          @click="editEvent"
        >
          <span class="material-symbols-outlined">edit</span>Edit
        </button>
      </div>
    </li>
  </ul>

  <div id="edit-event-form" :class="{ 'edit-event-form--visible': editing }">
    <h1>
      Edit Event
      <button id="close-edit-form-btn" @click="closeEditEvent">
        <span class="material-symbols-outlined">close</span>
      </button>
    </h1>
    <EventForm :existingEvent="event" />
  </div>
</template>

<script>
import { checkStatusCode } from "../utils";
import { jwtDecode } from "jwt-decode";

export default {
  data() {
    return {
      myTeams: [],
      baseUrl: window.location.hostname,
      token: null,
      myEvents: [],
      event: undefined,
      editing: false,
      myAttendance: {},
    };
  },
  mounted() {
    this.token = localStorage.getItem("jwtToken");
    const decodedToken = jwtDecode(this.token);
    this.id = decodedToken.id;

    fetch(`http://${this.baseUrl}:8080/api/teams`, {
      headers: {
        Authorization: "Bearer " + this.token,
      },
    })
      .then(checkStatusCode)
      .then((res) => res.json())
      .then((teams) => (this.teams = teams));

    this.getMyTeams();
  },
  methods: {
    deleteEvent(e) {
      const confirmation = confirm(
        "Are you sure you want to delete this event?"
      );
      if (confirmation) {
        const eventId = e.currentTarget.getAttribute("data-event-id");
        fetch(`http://${this.baseUrl}:8080/api/events/${eventId}`, {
          headers: {
            Authorization: "Bearer " + this.token,
          },
          method: "DELETE",
        })
          .then(checkStatusCode)
          .then(() => this.getMyEvents());
      }
    },
    editEvent(e) {
      const eventId = e.currentTarget.getAttribute("data-event-id");
      const existingEventEvent = this.myEvents.find(
        (event) => event.event_id == eventId
      );
      this.event = existingEventEvent;
      this.editing = true;
    },
    closeEditEvent() {
      this.editing = false;
    },
    toggleAttendance(e) {
      const eventId = e.currentTarget.getAttribute("data-event-id"),
        current_status =
          e.currentTarget.getAttribute("data-status") === "true" ? true : false;
      let scrollY = window.scrollY;
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
      )
        .then(checkStatusCode)
        .then(() => this.getMyEvents())
        .then(() => {
          setTimeout(() => {
            window.scrollTo(0, scrollY);
          }, 500);
        });
    },
    checkMyAttendance(eventId) {
      if (eventId in this.myAttendance) {
        return this.myAttendance[eventId];
      }
      return false;
    },
    formatDateTime(dateStr, startTime, endTime) {
      // some events have only date info and no time info
      // we need to deal with all these cases
      const timeFormat = {
        hour: "2-digit",
        minute: "2-digit",
        hour12: false,
      };

      const dateFormat = {
        year: "numeric",
        month: "long",
        day: "numeric",
      };

      const dateFormatter = new Intl.DateTimeFormat("en-IE", {
        ...dateFormat,
      });

      const dateTimeFormatter = new Intl.DateTimeFormat("en-IE", {
        ...dateFormat,
        ...timeFormat,
      });

      if (dateStr != null) {
        if ((startTime != null) & (endTime != null)) {
          const startDateTime = new Date(`${dateStr}T${startTime}`);
          const endDateTime = new Date(`${dateStr}T${endTime}`);

          const timeFormatter = new Intl.DateTimeFormat("en-IE", {
            ...timeFormat,
          });

          return (
            dateTimeFormatter.format(startDateTime) +
            " until " +
            timeFormatter.format(endDateTime)
          );
        }
        return (
          dateFormatter.format(new Date(`${dateStr}`)) +
          ". No time provided by organiser."
        );
      } else {
        return "No date/time info. provided by organiser.";
      }
    },
    getMyAttendance() {
      fetch(`http://${this.baseUrl}:8080/api/players/${this.id}/events`, {
        headers: {
          Authorization: "Bearer " + this.token,
        },
      })
        .then(checkStatusCode)
        .then((res) => res.json())
        .then((myAttendance) => (this.myAttendance = myAttendance));
    },
    getMyEvents() {
      this.myEvents = [];
      let myTeamIds = this.myTeams.map((team) => team.teamId);
      fetch(`http://${this.baseUrl}:8080/api/events`, {
        headers: {
          Authorization: "Bearer " + this.token,
        },
      })
        .then(checkStatusCode)
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
          this.getMyEvents();
        });
    },
  },
};
</script>

<style lang="scss">
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
  gap: 3em;
  align-items: center;
  .event-icon {
    font-size: 3.5em;
  }
  .event-details p {
    display: flex;
    align-items: center;
    margin: 0.5em 0;
    .material-symbols-outlined {
      margin-right: 0.5em;
    }
  }
}
.event-buttons {
  display: flex;
  flex-grow: 1;
  justify-content: flex-end;
  button {
    background: #686868;
    padding: 0.75em 1em;
    color: inherit;
    margin: 0 0.5em;
  }
}
.attend-btn div {
  font-weight: bold;
}
#edit-event-form {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: var(--color-background);
  padding: 15em;
  text-align: center;
  display: none;
  &.edit-event-form--visible {
    display: block;
  }
}
#close-edit-form-btn {
  appearance: none;
  margin-top: 1em;
  background: none;
  border: none;
  color: currentColor;
  cursor: pointer;
  .material-symbols-outlined {
    font-size: 2.5em;
    margin-left: 1em;
  }
}
.attendance-stats .material-symbols-outlined {
  vertical-align: middle;
  margin: 0 0.1em;
}
.attendance-stats {
  margin-top: 1em;
  cursor: default;
}
</style>
