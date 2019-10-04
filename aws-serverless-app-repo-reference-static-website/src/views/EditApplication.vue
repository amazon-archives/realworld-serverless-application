<template>
  <div>
    <Breadcrumb />
    <b-container>
      <ErrorAlert :message="error" />
      <b-row>
        <b-col>
          <h2>Edit {{ this.$route.params.id }}</h2>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <b-card title="Application details">
            <b-form @submit.prevent="onSubmit()">
              <b-form-group
                id="author-group"
                label="Author"
                label-for="author"
                description="Use a name you'd like to publish your application under.
                This name is visible to the public."
              >
                <b-form-input id="author" v-model="form.author" required />
              </b-form-group>
              <b-form-group
                id="homepage-url-group"
                label="Home page - optional"
                label-for="homepage-url"
                description="Point people to your application's home page (e.g. your GitHub repository URL)."
              >
                <b-form-input id="homepage-url" v-model="form.homePageUrl" />
              </b-form-group>
              <b-form-group
                id="description-group"
                label="Description"
                label-for="description"
                description="Help people understand what your app should be used for, and how it works."
              >
                <b-form-textarea id="description" v-model="form.description" required rows="3" />
              </b-form-group>

              <b-button type="submit" variant="primary" class="float-right ml-2">Save application</b-button>
              <b-button
                id="cancel"
                :to="{name: 'view-application', params: {id: this.$route.params.id}}"
                class="float-right"
              >Cancel</b-button>
            </b-form>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import Breadcrumb from '@/components/Breadcrumb.vue';
import ErrorAlert from '@/components/ErrorAlert.vue';
import { GET_APPLICATION, UPDATE_APPLICATION } from '../store/actions.type';

export default {
  name: 'edit-application',
  components: {
    Breadcrumb,
    ErrorAlert,
  },
  data() {
    return {
      form: {
        author: null,
        homePageUrl: null,
        description: null,
      },
      error: null,
    };
  },
  methods: {
    fetchApplication() {
      this.error = null;
      this.$store.dispatch(GET_APPLICATION, this.$route.params.id)
        .then((application) => {
          this.form.author = application.author;
          this.form.homePageUrl = application.homePageUrl;
          this.form.description = application.description;
        })
        .catch((error) => {
          this.error = error.message;
        });
    },
    onSubmit() {
      this.error = null;
      const request = {
        applicationId: this.$route.params.id,
        description: this.form.description,
        author: this.form.author,
        homePageUrl: this.form.homePageUrl,
      };
      this.$store.dispatch(UPDATE_APPLICATION, request)
        .then(() => {
          this.$router.push({
            name: 'view-application',
            params: { id: this.$route.params.id },
          });
        })
        .catch((error) => {
          this.error = error.message;
        });
    },
  },
  created() {
    this.fetchApplication();
  },
};
</script>
