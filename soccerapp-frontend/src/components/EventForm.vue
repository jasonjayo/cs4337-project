<!-- component used for creating new events and modifying existing ones  -->
<template>
  <p class="form-note">Mandatory fields are marked with a *.</p>
  <form @submit="submitForm" class="form-layout">
    <div class="form-group">
      <label for="title">Title*</label>
      <input required type="text" id="title" v-model="event.title" />
    </div>
    <div class="form-group">
      <label for="description">Description</label>
      <input type="text" id="description" v-model="event.description" />
    </div>
    <div class="form-group">
      <label for="eventDate">Date*</label>
      <input required type="date" id="eventDate" v-model="event.eventDate" />
    </div>
    <div class="form-group">
      <label for="startTime">Start Time</label>
      <input type="time" id="startTime" v-model="event.startTime" />
    </div>
    <div class="form-group">
      <label for="endTime">End Time</label>
      <input type="time" id="endTime" v-model="event.endTime" />
    </div>
    <div class="form-group">
      <label for="teamId">Team</label>
      <select required id="teamId" v-model="event.teamId">
        <option v-for="team in teams" :value="team.id">{{ team.name }}</option>
      </select>
    </div>
    <div class="form-group">
      <label for="location">Location</label>
      <input type="text" id="location" v-model="event.location" />
    </div>
    <div class="form-group">
      <label for="eventType">Type</label>
      <input type="text" id="eventType" v-model="event.eventType" />
    </div>
    <div class="form-group">
      <button type="submit">Submit</button>
    </div>
  </form>
</template>

<style lang="scss" scoped>
h1,
.form-note {
  text-align: center;
}
.form-note {
  margin-bottom: 0.75em;
}
.form-layout {
  display: grid;
  grid-template-columns: 100px 400px;
  gap: 16px;
  align-items: center;
  margin: auto;
  width: fit-content;
}

.form-group {
  display: contents;
}

label {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  padding-right: 8px;
  font-weight: bold;
}

input,
select {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  grid-column: 2;
  padding: 0.5em 2em;
  width: 100%;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  text-align: center;
  font: inherit;
}

button:hover {
  background-color: #0056b3;
}
</style>
<script>
import { checkStatusCode } from "../utils";

export default {
  data() {
    return {
      teams: [],
      baseUrl: window.location.hostname,
      existingId: undefined,
      event: {
        eventType: "",
        eventDate: new Date().toISOString().split("T")[0],
        location: "",
        description: "",
        title: "My New Event",
        startTime: "",
        endTime: "",
        teamId: "",
      },
    };
  },
  props: {
    // to facilitate modifying existing event, we have a prop to allow us to pass in an existing one
    existingEvent: {
      type: Object,
      required: false,
    },
  },
  watch: {
    // watch for the case where event we're modifying changes
    existingEvent(newExisting) {
      if (newExisting) {
        const { event_id, ...remainingValues } = newExisting;
        this.existingId = event_id;
        Object.assign(this.event, remainingValues);
      }
    },
  },
  mounted() {
    this.token = localStorage.getItem("jwtToken");

    fetch(`http://${this.baseUrl}:8080/api/teams`, {
      headers: {
        Authorization: "Bearer " + this.token,
      },
    })
      .then(checkStatusCode)
      .then((res) => res.json())
      .then((teams) => {
        // we need a teams array for we can display the teams by name in the form instead of just by ID
        teams.forEach((team) => {
          this.teams.push({
            id: team.teamId,
            name: team.teamName,
          });
        });
        this.event.teamId = this.teams[0].id;
      });
  },
  methods: {
    submitForm(e) {
      e.preventDefault();
      if (this.existingId) {
        // modifying existing event
        fetch(`http://${this.baseUrl}:8080/api/events/${this.existingId}`, {
          method: "PUT",
          headers: {
            Authorization: `Bearer ${this.token}`,
            "Content-Type": "application/json",
          },
          body: JSON.stringify(this.event),
        }).then((res) => {
          if (res.status === 200) {
            alert(`Event ${this.existingId} updated successfully!`);
            location.reload();
          } else {
            alert(
              "Failed to update event. Try refreshing the page to reauthenticate."
            );
          }
        });
      } else {
        // creating new event
        fetch(`http://${this.baseUrl}:8080/api/events`, {
          method: "POST",
          headers: {
            Authorization: `Bearer ${this.token}`,
            "Content-Type": "application/json",
          },
          body: JSON.stringify(this.event),
        }).then((res) => {
          if (res.status === 200) {
            alert("New event created successfully!");
          } else {
            alert(
              "Failed to create event. Try refreshing the page to reauthenticate."
            );
          }
        });
      }
    },
  },
};
</script>
