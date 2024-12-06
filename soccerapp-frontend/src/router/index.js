// here we define the routes for our web app

import { createRouter, createWebHistory } from "vue-router";
import Homepage from "@/components/Homepage.vue";
import OAuth2RedirectHandler from "@/components/OAuth2RedirectHandler.vue";
import Dashboard from "@/components/Dashboard.vue";
import CreateEvent from "@/components/CreateEvent.vue";
import Events from "@/components/Events.vue";

const routes = [
  { path: "/", name: "Homepage", component: Homepage },
  { path: "/dashboard", name: "DashboardPage", component: Dashboard },
  { path: "/create-event", name: "CreateEventPage", component: CreateEvent },
  { path: "/events", name: "EventsPage", component: Events },
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

// prohibit access to user routes if not authenticated
router.beforeEach((to, from, next) => {
  if (!["/", "/oauth2/redirect"].includes(to.path)) {
    const authToken = localStorage.getItem("jwtToken");
    if (!authToken) {
      alert("You must be logged in to access this!");
      return next({ path: "/" });
    }
  }
  next();
});

export default router;
