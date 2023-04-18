<template>
<MainNavbarVue />

  <div class="profile">
    <h2>Profile Page</h2>

    <form v-if="customer">
      <label for="fullname">Full Name</label>
      <input type="text" id="fullname" v-model="customer.fullName" />
      <label for="email">Email</label>
      <input type="email" id="email" v-model="customer.email" />
      <label for="phone">Phone Number</label>
      <input type="tel" id="phone" v-model="customer.phoneNumber" /><br />
      <a class="btn btn-outline-primary" id="updateBtn" @click="updateProfileInfo" role="button">Update</a >
    </form>

    <span v-if="customerInfo.errorMessage" style="color: #8b0000">{{ customerInfo.errorMessage }}</span><br v-if="customerInfo.errorMessage" />
    
    <img src="@/assets/images/Enfoy.webp" alt="" />
    <div class="links">
      <a class="link" href="#/profile/" id="thisProfile">Profile</a>
      <a class="link" id="rightLink" href="#/profile/booking">Your Booking</a>
    </div>
  </div>

<MainFooter />
</template>

<script lang="ts">
import MainNavbarVue from "@/components/MainNavbar.vue";
import MainFooter from "@/components/MainFooter.vue";
import { defineComponent, reactive, ref } from "vue";
import ProfileService from "@/api/ProfileServiceApi";
import { getEmail, logout, setToken } from "@/utils";
import router from "@/router";

interface Customer {
  id: number;
  fullName: string;
  email: string;
  phoneNumber: string;
}

export default defineComponent({
  components: {
    MainNavbarVue,
    MainFooter,
  },
  setup() {
    const customer = ref<Customer>();
    const customerInfo = reactive({
      id: "",
      fullName: "",
      email: "",
      phoneNumber: "",
      errorMessage: "",
    });

    const getCustomerInfo = async () => {
      try {
        setToken();
        let email = getEmail();
        const response = await ProfileService.getProfileInfo(email);
        customer.value = response.data;
      } catch (error) {
        console.log(error);
      }
    };

    getCustomerInfo();

    async function updateProfileInfo() {
      try {
        const data = {
          id: customer.value?.id,
          fullName: customer.value?.fullName,
          email: customer.value?.email,
          phoneNumber: customer.value?.phoneNumber,
        };
        await ProfileService.updateProfileInfo(data);
        logout();
        router.push({ path: "/auth/authenticate" });
      } catch (error) {
        customerInfo.errorMessage = error.response.data;
      }
    }

    return {
      customer,
      customerInfo,
      updateProfileInfo,
    };
  },
});
</script>

<style scoped>
.profile {
  max-width: 1000px;
  margin: 0 auto;
  align-items: center;
  padding: 40px;
  position: relative;
}

img {
  max-width: 300px;
  margin-left: 20px;
  position: fixed;
  top: 15%;
  right: 20%;
}

form {
  margin-top: 20px;
}

label {
  display: block;
  margin-bottom: 5px;
}

input {
  width: 50%;
  padding: 8px;
  margin-bottom: 10px;
}

#updateBtn {
  background-color: #007bff;
  color: #fff;
  padding: 10px 20px;
  border: none;
  cursor: pointer;
}

.links {
  margin-top: 20px;
}

#thisProfile {
  font-weight: bold;
}

#rightLink {
  margin-left: 20px;
}
</style>