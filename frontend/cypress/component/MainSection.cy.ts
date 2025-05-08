import MainSection from '../../src/sections/MainSection.vue'

describe('<MainSection />', () => {
  it('mounts', () => {
    cy.mount(MainSection)
  })

  it('mounts with list', () => {
    cy.mount(MainSection, {
      props: {
        list: {
          "id": 1,
          "title": "Groceries",
          "todos": [
            { "id": 1, "description": "Milk", complete: false },
            { "id": 2, "description": "Bread", complete: false },
            { "id": 3, "description": "Butter", complete: false }
          ]
        }
      }
    })
  })
})
