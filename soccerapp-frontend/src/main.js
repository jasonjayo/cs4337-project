import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";

import "./assets/main.css";
import EventForm from "./components/EventForm.vue";

let app = createApp(App);

app.component("EventForm", EventForm);

app.use(router);
app.mount("#app");
