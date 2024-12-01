import { createRouter, createWebHistory } from "vue-router";
import Homepage from "@/components/Homepage.vue";
import TeamPage from "@/components/team.vue";
import OAuth2RedirectHandler from "@/components/OAuth2RedirectHandler.vue";
import Dashboard from "@/components/Dashboard.vue";
import CreateEvent from "@/components/CreateEvent.vue";

const routes = [
  { path: "/", name: "Homepage", component: Homepage },
  { path: "/team", name: "TeamPage", component: TeamPage },
  { path: "/dashboard", name: "DashboardPage", component: Dashboard },
  { path: "/create-event", name: "CreateEventPage", component: CreateEvent },
  {
    path: "/oauth2/redirect",
    name: "OAuthRedirect",
    component: OAuth2RedirectHandler,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
