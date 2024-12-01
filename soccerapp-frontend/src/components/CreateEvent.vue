<template>
  <h1>Create New Event</h1>
  <p class="form-note">Mandatory fields are marked with a *.</p>
  <form @submit="createNewEvent" class="form-layout">
    <div class="form-group">
      <label for="title">Title*</label>
      <input required type="text" id="title" v-model="newEvent.title" />
    </div>
    <div class="form-group">
      <label for="description">Description</label>
      <input type="text" id="description" v-model="newEvent.description" />
    </div>
    <div class="form-group">
      <label for="eventDate">Date*</label>
      <input required type="date" id="eventDate" v-model="newEvent.eventDate" />
    </div>
    <div class="form-group">
      <label for="startTime">Start Time</label>
      <input type="time" id="startTime" v-model="newEvent.startTime" />
    </div>
    <div class="form-group">
      <label for="endTime">End Time</label>
      <input type="time" id="endTime" v-model="newEvent.endTime" />
    </div>
    <div class="form-group">
      <label for="teamId">Team</label>
      <select required id="teamId" v-model="newEvent.teamId">
        <option v-for="team in teams" :value="team.id">{{ team.name }}</option>
      </select>
    </div>
    <div class="form-group">
      <label for="location">Location</label>
      <input type="text" id="location" v-model="newEvent.location" />
    </div>
    <div class="form-group">
      <label for="eventType">Type</label>
      <input type="text" id="eventType" v-model="newEvent.eventType" />
    </div>
    <div class="form-group">
      <button type="submit">Submit</button>
    </div>
  </form>
</template>
<script>
export default {
  name: "CreateEvent",
  methods: {
    createNewEvent(e) {
      e.preventDefault();
      fetch(`http://${this.baseUrl}:8080/api/events`, {
        method: "POST",
        headers: {
          Authorization: `Bearer ${this.token}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify(this.newEvent),
      }).then((res) => {
        if (res.status === 200) {
          alert("New event created successfully!");
        }
      });
    },
  },
  data() {
    return {
      baseUrl: window.location.hostname,
      token: localStorage.getItem("jwtToken"),
      teams: [],
      newEvent: {
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
  mounted() {
    fetch(`http://${this.baseUrl}:8080/api/teams`, {
      headers: {
        Authorization: "Bearer " + this.token,
      },
    })
      .then((res) => res.json())
      .then((teams) => {
        teams.forEach((team) => {
          this.teams.push({
            id: team.teamId,
            name: team.teamName,
          });
        });
        this.newEvent.teamId = this.teams[0].id;
      });
  },
};
</script>
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
