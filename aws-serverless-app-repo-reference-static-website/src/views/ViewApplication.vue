<template>
  <div>
    <Breadcrumb />
    <b-container>
      <ErrorAlert :message="error" />
      <b-row>
        <b-col>
          <h2 v-if="application">{{ application.applicationId }}</h2>
        </b-col>
      </b-row>
      <b-row class="mb-3">
        <b-col>
          <b-button
            id="edit"
            v-if="application"
            class="float-right"
            :to="{name: 'edit-application', params: {id: application.applicationId}}"
          >Edit</b-button>
          <b-button
            id="delete"
            v-if="application"
            v-b-modal.delete-modal
            variant="danger"
            class="float-right mr-2"
          >Delete</b-button>
          <b-modal
            id="delete-modal"
            title="Delete application"
            ok-title="Delete application"
            @ok="deleteApplication()"
          >
            <p>Are you sure you want to delete this application?</p>
          </b-modal>
        </b-col>
      </b-row>
      <b-row class="mt-3">
        <b-col>
          <b-card title="Application details">
            <b-card-body>
              <b-row>
                <b-col>
                  <div>
                    <strong>Name</strong>
                  </div>
                  <div v-if="application">{{ application.applicationId }}</div>
                </b-col>
              </b-row>
              <b-row class="mt-3">
                <b-col>
                  <div>
                    <strong>Author</strong>
                  </div>
                  <div v-if="application">{{ application.author }}</div>
                </b-col>
              </b-row>
              <b-row class="mt-3">
                <b-col>
                  <div>
                    <strong>Home Page</strong>
                  </div>
                  <div v-if="application">{{ application.homePageUrl }}</div>
                </b-col>
              </b-row>
              <b-row class="mt-3">
                <b-col>
                  <div>
                    <strong>Creation time</strong>
                  </div>
                  <div v-if="application">{{ application.creationTime.toLocaleString('en-US') }}</div>
                </b-col>
              </b-row>
              <b-row class="mt-3">
                <b-col>
                  <div>
                    <strong>Description</strong>
                  </div>
                  <div v-if="application">{{ application.description }}</div>
                </b-col>
              </b-row>
            </b-card-body>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import Breadcrumb from '@/components/Breadcrumb.vue';
import ErrorAlert from '@/components/ErrorAlert.vue';
import { GET_APPLICATION, DELETE_APPLICATION } from '../store/actions.type';

export default {
  name: 'view-application',
  components: {
    Breadcrumb,
    ErrorAlert,
  },
  data() {
    return {
      application: null,
      error: null,
    };
  },
  methods: {
    fetchApplication() {
      this.error = null;
      this.$store.dispatch(GET_APPLICATION, this.$route.params.id)
        .then((application) => {
          this.application = application;
        })
        .catch((error) => {
          this.error = error.message;
        });
    },
    deleteApplication() {
      this.error = null;
      this.$store.dispatch(DELETE_APPLICATION, this.$route.params.id)
        .then(() => {
          this.$router.push({ name: 'applications' });
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
