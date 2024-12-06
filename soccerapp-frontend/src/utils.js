// utility functions used across our app

function checkStatusCode(res) {
  // if not authenticated - likely token has expired, so redirect to auth to get a new one
  if (res.status === 401) {
    window.location.href = `http://${window.location.hostname}:8080/oauth2/authorization/google`;
  } else {
    return res;
  }
}

export { checkStatusCode };
