<template>
  <div id="auth">
    <div class="image">
      <img src="@/assets/images/Reg.webp" alt="" />
    </div>

    <div class="form">
      <h2>Authorization</h2><br />
      
      <input type="text" placeholder="Email" id="email" v-model="userInfo.email" maxlength="70"/><br />

      <input type="password" placeholder="Password" id="password" v-model="userInfo.password" /><br />

      <div v-if="errorMessage === '401'" style="color: #8b0000"> Wrong Login or password </div><br v-if="errorMessage === '401'" />

      <button class="btn btn-outline-primary" @click="authUser()">Auth</button>
    </div>
  </div>
</template>
  
<script lang="ts">
import { defineComponent, reactive, ref } from "vue";
import AuthServiceApi from "@/api/AuthServiceApi";
import axios from "axios";
import router from "@/router";

export default defineComponent({
  setup() {
    const userInfo = reactive({
      email: "",
      password: "",
    });

    const errorMessage = ref("");

    async function authUser() {
      try {
        const response = await AuthServiceApi.login({
          email: userInfo.email,
          password: userInfo.password,
        });

        const token = response.data.token;
        const refreshToken = response.data.refreshToken;
        axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
        localStorage.setItem(
          "authorizationToken",
          axios.defaults.headers.common["Authorization"]
        );
        localStorage.setItem("refreshToken", refreshToken);
        router.push({ path: "/myhotel/" });
      } catch (error) {
        errorMessage.value = error.response.status.toString();
      }
    }
    return {
      userInfo,
      errorMessage,
      authUser,
    };
  },
});
</script>
  
<style scoped>
#auth {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: white;
}

.image {
  flex-basis: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  min-width: 50%;
  min-height: 50%;
}

.form {
  flex-basis: 50%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-bottom: 100px;
}

img {
  max-width: 100%;
  min-width: none;
  min-height: 50%;
  max-height: 100%;
  width: 100%;
}

input {
  border: 1px solid #39a0ca;
  border-radius: 5px 5px 5px 5px;
  width: 30%;
  height: 30px;
}

button {
  width: 100px;
}
</style>
  
  