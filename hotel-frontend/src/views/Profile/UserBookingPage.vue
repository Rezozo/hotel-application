<template>
<MainNavbar />

  <div id="booking">
    <h2>Your Booking</h2>

    <div id="book">
      <div v-for="book in bookingInfo" :key="book.id" class="book-bubble">
        <div class="book-allInfo">
          <div class="book-info">Room title: {{ book.roomTitle }}</div>

          <div class="book-info">Arrival date: {{ formatDate(book.arrivalDate) }}</div>

          <div class="book-info">Departure date: {{ formatDate(book.departureDate) }}</div>
          
          <div class="book-info">Total cost: {{ book.cost }} $</div>

          <button class="btn btn-info" v-if="isDeleteVisible(book.arrivalDate)" @click="deleteBooking(book.id)">Delete</button>
        </div>
      </div>
    </div>
  </div>

<MainFooter />
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import MainNavbar from "@/components/MainNavbar.vue";
import MainFooter from "@/components/MainFooter.vue";
import ProfileService from "@/api/ProfileServiceApi";
import { getEmail, setToken } from "@/utils";

interface Booking {
  id: number;
  roomTitle: string;
  arrivalDate: string;
  departureDate: string;
  cost: number;
}

export default defineComponent({
  components: {
    MainNavbar,
    MainFooter,
  },
  setup() {
    const bookingInfo = ref<Booking[]>([]);

    const getBooking = async () => {
      try {
        setToken();
        let email = getEmail();
        const response = await ProfileService.getAllBooking(email);
        bookingInfo.value = response.data;
      } catch (error) {
        console.log(error);
      }
    };

    getBooking();

    const isDeleteVisible = (arrivalDate: string) => {
      const today = new Date();
      const arrival = new Date(arrivalDate);
      return today < arrival;
    };

    const deleteBooking = async (bookingId: number) => {
      try {
        setToken();
        await ProfileService.deleteBooking(bookingId);
        await getBooking();
      } catch (error) {
        console.log(error);
      }
    };

    function formatDate(date: string) {
      const newDate = new Date(date);
      return newDate.toISOString().split("T")[0];
    }

    return {
      bookingInfo,
      isDeleteVisible,
      deleteBooking,
      formatDate,
    };
  },
});
</script>

<style scoped>
h2 {
  margin: 20px 0;
  text-align: center;
}

#book {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.book-bubble {
  border: 1px solid #ccc;
  background-color: #f0f8ff;
  padding: 20px 20px 5px 20px;
  border-radius: 10px;
  margin-bottom: 20px;
  width: 100%;
  max-width: 400px;
}

.book-allInfo {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-bottom: 10px;
}

.book-info {
  margin-bottom: 10px;
  font-size: 20px;
}

button {
  width: 100px;
  height: 40px;
}
</style>