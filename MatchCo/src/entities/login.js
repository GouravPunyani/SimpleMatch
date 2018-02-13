// @flow
import { observable, action, computed } from 'mobx'
import Validate from 'validate.js'
import I18n from 'react-native-i18n'
import { memoize, forEach, isNil, curry, keys, isEmpty } from 'ramda'

// gets the constraints, but delayed in a function due to a timing issue
// with i18n.
const getConstraints = memoize(() => {
  return {
    email: {
      presence: { message: I18n.t('errors.invalidEmail') },
      email: { message: I18n.t('errors.invalidEmail') }
    },
    password: {
      presence: { message: I18n.t('errors.passwordRequired') }
    }
  }
})

export class Login {
  @observable email: string
  @observable password: string

  @observable errors: any = {
    email: null,
    password: null
  }

  /**
   * Updates a field key with this new value.
   */
  update = action(curry(
    (key: string, value: string) => {
      this[key] = value
      this.validate(key)
    }
  ))

  @action
  async validate (...fields: string[]) {
    const currentErrors = this.errors
    const isSubset = fields.length > 0

    // grab all the errors
    const errors = Validate(this, getConstraints(), { fullMessages: false }) || {}

    // determine which fields to update
    const fieldsToUpdate = isEmpty(fields) || keys(currentErrors)

    // for subsets, reset any errors that may be error-free now
    if (isSubset && !isEmpty(errors)) {
      forEach(field => {
        if (isNil(errors[field])) currentErrors[field] = null
      }, fields)
    }

    // set each error
    forEach(field => {
      currentErrors[field] = errors[field]
    }, fieldsToUpdate)

    return isEmpty(errors)
  }

  @computed get isValid () {
    return isNil(Validate(this, getConstraints()))
  }
}
