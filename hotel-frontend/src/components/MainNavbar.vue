<template>
  <nav class="navbar navbar-expand-lg navbar-light custom-navbar">
    <a id="leftSide" class="navbar-brand" href="#/myhotel/">Myhotel</a>
    <img id="logo" src="@/assets/images/Logo.webp" alt="logo" />
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavDropdown">
      <ul class="navbar-nav">
        <li class="nav-item active">
          <a class="nav-link" href="#/myhotel/">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#/myhotel/allrooms">AllRooms</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#/reviews/">Reviews</a>
        </li>
      </ul>
    </div>
    <div v-if="hasTokenOrRefresh()">
      <a id="btnProfile" class="btn btn-outline-dark" href="#/profile/" role="button" >Profile</a >
      <a id="btnLogOut" class="btn btn-outline-danger" href="#/myhotel/" @click="logout" role="button" >Logout</a >
    </div>
    <div v-else>
      <a id="btnLogin" class="btn btn-outline-dark" href="#/auth/authenticate" role="button" >Log in</a >
      <a id="btnJoin" class="btn btn-outline-danger" href="#/auth/register" role="button" >Join</a >
    </div>
  </nav>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import hasTokenOrRefresh, { logout } from "@/utils";

export default defineComponent({
  setup() {
    return {
      hasTokenOrRefresh,
      logout,
    };
  },
});
</script>

<style>
.custom-navbar {
  background-color: white;
  box-shadow: 0px 0px 4px black;
  max-width: 2000px;
  margin: 0 auto;
}

#logo {
  width: 25px;
  height: 25px;
}

#leftSide {
  margin-left: 20px;
}

li {
  margin-left: 70px;
}

a {
  font-size: 25px;
  color: black;
}

nav {
  margin-bottom: 20px;
}

#btnLogin {
  width: 100px;
}

#btnJoin {
  width: 70px;
  margin-left: 30px;
  margin-right: 20px;
}

#btnProfile {
  width: 100px;
}

#btnLogOut {
  width: 80px;
  margin-left: 30px;
  margin-right: 20px;
}
</style>