<template>
  <div>
    <Breadcrumb />
    <b-container>
      <ErrorAlert :message="error" />
      <b-row>
        <b-col>
          <h2>My applications</h2>
        </b-col>
      </b-row>
      <b-row class="mb-3">
        <b-col>
          <b-button
            variant="primary"
            class="float-right"
            :to="{name: 'new-application'}"
          >Publish application</b-button>
        </b-col>
      </b-row>
      <b-row>
        <b-table striped :items="applications" :fields="fields" primary-key="applicationId" small>
          <template v-slot:cell(applicationId)="data">
            <router-link
              :to="{name: 'view-application', params: {id: data.item.applicationId}}"
            >{{ data.item.applicationId }}</router-link>
          </template>
          <template
            v-slot:cell(creationTime)="data"
          >{{ new Date(data.item.creationTime).toLocaleDateString('en-US') }}</template>
        </b-table>

        <b-container class="text-center" v-if="applications !== null && applications.length === 0">
          Once you publish an application, you'll see it here. To get started, click the 'Publish
          application' button.
        </b-container>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import Breadcrumb from '@/components/Breadcrumb.vue';
import ErrorAlert from '@/components/ErrorAlert.vue';
import { LIST_APPLICATIONS } from '../store/actions.type';

export default {
  name: 'applications',
  components: {
    Breadcrumb,
    ErrorAlert,
  },
  data() {
    return {
      applications: null,
      error: null,
      fields: [
        {
          key: 'applicationId',
          label: 'Application name',
        },
        {
          key: 'description',
          label: 'Description',
        },
        {
          key: 'creationTime',
          label: 'Creation time',
        },
      ],
    };
  },
  methods: {
    fetchApplications() {
      this.$store.dispatch(LIST_APPLICATIONS)
        .then((applications) => {
          this.applications = applications;
        })
        .catch((error) => {
          this.error = error.message;
        });
    },
  },

  created() {
    this.fetchApplications();
  },
};
</script>
