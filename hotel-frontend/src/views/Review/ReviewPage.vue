<template>
<MainNavbar />

  <div class="reviews">
    <h2 id="reviewH2">Reviews about our hotel</h2>

    <div id="review">
      <div v-for="review in visibleReviews" :key="review.id" class="review-bubble">
        <div class="review-info">
          <div class="review-user">{{ review.fullName }}</div>
          <div class="review-rating">Rate: {{ review.rate }}</div>
        </div>

        <div class="review-text">{{ review.feedback }}</div>

        <div class="d-flex justify-content-center align-items-center">
          <button id="btnDelete" class="btn btn-dark" v-if="checkUserName(review.email)" @click="deleteReview(review.id)">Delete</button>
        </div>
      </div>
    </div>

    <div class="shwMoreBtn" v-if="visibleReviews.length < reviews.length">
      <button class="btn btn-dark" @click="showMoreReviews">Show more</button>
    </div>

    <div id="addRev" v-if="hasTokenOrRefresh()">
      <hr id="blackLine" />
      <h2 id="addH2">Add your review!</h2>

      <div id="formToAdd">
        <div class="input-group">
          <div div class="input-group-prepend">
            <label class="input-group-text">Email</label>
          </div>
          <input type="text" class="form-control" placeholder="Your Email" v-model="reviewInfo.email"/>
        </div>

        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <label class="input-group-text" for="inputGroupSelect01">Rate</label>
          </div>
          <select class="custom-select" id="inputGroupSelect01" v-model="reviewInfo.rate">
            <option selected value="">Your Rate</option>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
          </select>
        </div>

        <textarea class="form-control input-group" id="textarea" rows="3" placeholder="Your Feedback" v-model="reviewInfo.feedback"></textarea>

        <span v-if="reviewInfo.errorMessage" style="color: #8b0000">{{ reviewInfo.errorMessage }}</span><br v-if="reviewInfo.errorMessage" />

        <div class="divButtonAdd">
          <button id="addButton" class="btn btn-dark" @click="addNewReview">Add review</button>
        </div>
      </div>
    </div>
  </div>
  
<MainFooter />
</template>

<script lang="ts">
import { defineComponent, reactive, ref } from "vue";
import ReviewService from "@/api/ReviewServiceApi";
import MainNavbar from "@/components/MainNavbar.vue";
import MainFooter from "@/components/MainFooter.vue";
import hasTokenOrRefresh, { checkUserName } from "@/utils";

interface Review {
  id: number;
  fullName: string;
  email: string;
  rate: number;
  feedback: string;
}

export default defineComponent({
  components: {
    MainNavbar,
    MainFooter,
  },
  setup() {
    const reviews = ref<Review[]>([]);
    const visibleReviews = ref<Review[]>([]);
    const reviewsToShow = ref<number>(5);
    const reviewInfo = reactive({
      email: "",
      rate: "",
      feedback: "",
      errorMessage: "",
    });

    const getReviews = async () => {
      try {
        const response = await ReviewService.getReviews();
        reviews.value = response.data;
        showMoreReviews();
      } catch (error) {
        console.error(error);
      }
    };

    const showMoreReviews = () => {
      visibleReviews.value = reviews.value.slice(0, reviewsToShow.value);
      reviewsToShow.value += 5;
    };

    getReviews();

    async function addNewReview() {
      try {
        const data = {
          email: reviewInfo.email,
          rate: reviewInfo.rate,
          feedback: reviewInfo.feedback,
        };
        if (checkUserName(data.email)) {
          await ReviewService.addNewReview(data).then(() => location.reload());
        } else {
          reviewInfo.errorMessage = "Write your email";
        }
      } catch (error) {
        reviewInfo.errorMessage = error.response.data;
      }
    }

    const deleteReview = (id: any) => {
      try {
        ReviewService.deleteReview(id).then(() => location.reload());
      } catch (error) {
        console.error(error);
      }
    };

    return {
      reviews,
      visibleReviews,
      reviewInfo,
      showMoreReviews,
      hasTokenOrRefresh,
      addNewReview,
      checkUserName,
      deleteReview,
    };
  },
});
</script>

<style scoped>
#reviewH2 {
  margin: auto 0;
  text-align: center;
  padding-top: 20px;
}

#addH2 {
  margin: auto 0;
  text-align: center;
}
.reviews {
  max-width: 850px;
  margin: 0 auto;
  padding-bottom: 20px;
}

#review {
  margin-bottom: 20px;
}

.review-bubble {
  border: 1px solid #ccc;
  background-color: #f0f8ff;
  padding: 20px;
  border-radius: 10px;
  position: relative;
  margin-top: 30px;
}

.review-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.review-user {
  font-weight: bold;
}

.review-rating {
  font-size: 20px;
  font-weight: bold;
  color: #ffd700;
}

.review-text {
  font-size: 15px;
}

#btnDelete {
  margin-bottom: -10px;
  width: 100px;
}

.divButtonAdd {
  text-align: center;
  justify-content: center;
  padding-bottom: 10px;
}

#addRev {
  max-width: 850px;
  margin: 0 auto;
}

#blackLine {
  width: 30%;
  border: 0, 8px solid black;
}

#formToAdd {
  margin-top: 10px;
  border: 1px solid #ccc;
  background-color: #f0f8ff;
  border-radius: 5px;
}

.input-group {
  width: 80%;
  margin-left: 30px;
  margin-top: 20px;
}

#textarea {
  max-height: 500px;
  min-height: 200px;
  margin-bottom: 20px;
}

span {
  margin-left: 30px;
}

label {
  background-color: #87ceeb;
}

#addButton {
  background-color: #87ceeb;
  border: none;
  color: #495057;
}

.shwMoreBtn {
  text-align: center;
}
</style>
