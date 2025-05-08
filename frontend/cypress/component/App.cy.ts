import App from "../../src/App.vue";

describe('<App />', () => {
  it('mounts', () => {
    cy.mount(App)
  })
})
