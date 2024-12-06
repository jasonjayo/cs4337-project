// set up Vue app
import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";

import "./assets/main.css";
import EventForm from "./components/EventForm.vue";

let app = createApp(App);

// component registration
app.component("EventForm", EventForm);

// use Vue router for navigation
app.use(router);
app.mount("#app");
