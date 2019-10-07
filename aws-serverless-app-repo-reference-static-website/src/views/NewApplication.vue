<template>
  <div>
    <Breadcrumb />
    <b-container>
      <b-row>
        <b-col>
          <h2>Publish an application</h2>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <b-card title="Application details">
            <b-form @submit.prevent="onSubmit()">
              <b-form-group
                id="application-id-group"
                label="Application name"
                label-for="application-id"
                description="Choose a short, memorable name that describes what your app does."
              >
                <b-form-input
                  id="application-id"
                  v-model="form.applicationId"
                  required
                  placeholder="my-application"
                />
              </b-form-group>
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

              <ErrorAlert :message="error" />

              <b-button type="submit" variant="primary" class="float-right ml-2">Publish application</b-button>
              <b-button id="cancel" to="/applications" class="float-right">Cancel</b-button>
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
import { CREATE_APPLICATION } from '../store/actions.type';

export default {
  name: 'new-application',
  components: {
    Breadcrumb,
    ErrorAlert,
  },
  data() {
    return {
      form: {
        applicationId: null,
        author: null,
        homePageUrl: null,
        description: null,
      },
      error: null,
    };
  },
  methods: {
    onSubmit() {
      this.error = null;
      const payload = {
        applicationId: this.form.applicationId,
        author: this.form.author,
        description: this.form.description,
      };
      if (this.form.homePageUrl) {
        payload.homePageUrl = this.form.homePageUrl;
      }
      this.$store.dispatch(CREATE_APPLICATION, payload)
        .then(() => {
          this.$router.push({
            name: 'view-application',
            params: { id: this.form.applicationId },
          });
        })
        .catch((error) => {
          this.error = error.message;
        });
    },
  },
};
</script>
