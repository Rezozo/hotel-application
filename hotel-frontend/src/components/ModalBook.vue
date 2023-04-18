<template>
  <div class="modal-wrapper" v-if="showModal">
    <div class="modal-overlay" @click="closeModal"></div>
    <div class="modal-content">
      <h2 style="text-align: center;">Booking Form</h2>
      <form>
        <label for="fullname">Full Name</label>
        <input type="text" id="fullname" v-model="bookingInfo.fullName" />

        <label for="phone">Phone Number</label>
        <input type="tel" id="phone" v-model="bookingInfo.phoneNumber" />

        <label for="phone">Room Title</label>
        <input type="title" id="roomTitle" v-model="roomTitleParams" readonly />

        <label for="arrival">Arrival Date</label>
        <input type="date" id="arrival" v-model="bookingInfo.arrivalDate" @change="sortByDate" />

        <label for="departure">Departure Date</label>
        <input type="date" id="departure" v-model="bookingInfo.departureDate" @change="sortByDate" />
        
        <h2 style="margin-top: 10px">Total Cost: {{ totalCost() }}</h2>
        <span v-if="bookingInfo.errorMessage" style="color: #8b0000">{{ bookingInfo.errorMessage }}</span><br v-if="bookingInfo.errorMessage" />

        <button type="submit" class="btn btn-primary" @click="bookRoom">Book</button>
      </form>
    </div>
  </div>
</template>
  
<script lang="ts">
import { defineComponent, reactive, ref } from "vue";
import BookingService from "@/api/BookingServiceApi";
import { RouteLocationNormalized, useRoute } from "vue-router";
import router from "@/router";

export default defineComponent({
  props: {
    price: {
      type: Number,
      required: true,
    },
  },
  setup(props) {
    const bookingInfo = reactive({
      fullName: "",
      phoneNumber: "",
      roomTitle: "",
      arrivalDate: "",
      departureDate: "",
      cost: props.price,
      errorMessage: "",
    });
    const showModal = ref(true);
    const route: RouteLocationNormalized = useRoute();
    let roomTitleParams = route.params.title.toString();

    const closeModal = () => {
      showModal.value = false;
      location.reload();
    };

    const sortByDate = () => {
      if (
        (bookingInfo.departureDate == "" && bookingInfo.arrivalDate != null) ||
        (bookingInfo.arrivalDate != "" &&
          bookingInfo.departureDate != null &&
          bookingInfo.departureDate < bookingInfo.arrivalDate)
      ) {
        bookingInfo.departureDate = bookingInfo.arrivalDate;
      }

      if (bookingInfo.departureDate != null && bookingInfo.arrivalDate == "") {
        bookingInfo.arrivalDate = bookingInfo.departureDate;
      }
    };

    const totalCost = () => {
      const arrivalDate = new Date(bookingInfo.arrivalDate);
      const departureDate = new Date(bookingInfo.departureDate);
      const timeDifference = departureDate.getTime() - arrivalDate.getTime();
      const daysDifference = Math.ceil(timeDifference / (1000 * 3600 * 24));
      const total = daysDifference * bookingInfo.cost;
      if (Number.isNaN(total)) {
        return 0;
      } else if (total == 0) {
        return bookingInfo.cost;
      }
      return total;
    };

    async function bookRoom() {
      try {
        const data = {
          fullName: bookingInfo.fullName,
          phoneNumber: bookingInfo.phoneNumber,
          roomTitle: roomTitleParams,
          arrivalDate: bookingInfo.arrivalDate,
          departureDate: bookingInfo.departureDate,
        };
        await BookingService.addNewBooking(data);
        router.push({ path: "/profile/booking" });
      } catch (error) {
        bookingInfo.errorMessage = error.response.data;
      }
    }

    return {
      bookingInfo,
      showModal,
      roomTitleParams,
      closeModal,
      sortByDate,
      totalCost,
      bookRoom,
    };
  },
});
</script>
  
<style scoped>
.modal-wrapper {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
}

.modal-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
  padding: 20px;
  max-width: 500px;
  width: 100%;
  border-radius: 5px;
}

form {
  display: flex;
  flex-direction: column;
}

label {
  margin-top: 10px;
  font-size: 16px;
}

input,
button {
  margin-top: 5px;
}
</style>