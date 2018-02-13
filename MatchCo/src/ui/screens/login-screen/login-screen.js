// @flow
import React from 'react'
import { Keyboard, Alert, Dimensions } from 'react-native'
import {
  Button,
  ButtonPanel,
  StaticLayout,
  Text,
  TextField,
  Form,
  KeyboardSpacer
} from '../..'
import { spacing } from '../../theme'
import glam from 'glamorous-native'
import { Login } from '../../../entities/login'
import { observer, inject } from 'mobx-react'
import { UserStore } from '../../../stores/user-store'
import { translator } from '../../../services/translate/translator'
const { width, height } = Dimensions.get('window')

const TextWithSmallGap = glam(Text)({
  paddingVertical: spacing.extraSmall,
  textAlign: 'center'
})

const OrText = glam(Text)({
  paddingVertical: spacing.medium,
  textAlign: 'center'
})

const Stretchy = glam.view({ height: height*.2 })

export type Props = {
  userStore: UserStore,
  navigationStore: NavigationStore,
  translate: Function
};

@translator
@inject('userStore', 'navigationStore')
@observer
export class LoginScreen extends React.Component<void, Props, void> {
  // the login entity
  login = new Login()

  /** The user has pressed the login button. */
  onPressLogin = async () => {
    const { email, password, validate } = this.login
    const { userStore, navigationStore, translate } = this.props

    // jet if we're not valid
    if (!validate()) return

    Keyboard.dismiss()

    // attempt login
    await userStore.login(email, password)

    // did we win?
    if (userStore.isLoggedIn) {
      navigationStore.navigateTo('dashboardStack')
      return
    }

    const title = translate('loginScreen.loginErrorTitle')
    const message = translate(userStore.user.errorCode === 'wrong'
      ? 'loginScreen.credentialsError'
      : 'loginScreen.unknownError'
    )

    Alert.alert(title, message)
  }

  render () {
    const { navigationStore } = this.props
    const { user } = this.props.userStore
    const { login } = this
    return (
      <StaticLayout>
          <Button.Facebook tx='loginScreen.loginWithFacebook' />
          <OrText tx='common.or' />
          <Form entity={login}>
            <TextField
              entity={login}
              field='email'
              disabled={user.fetching}
              value={login.email}
              errors={login.errors.email}
              labelTx='loginScreen.emailLabel'
            />
            <TextField
              password
              disabled={user.fetching}
              entity={login}
              field='password'
              value={login.password}
              errors={login.errors.password}
              labelTx='loginScreen.passwordLabel'
            />
          </Form>
          <Button
            tx='loginScreen.login'
            onPress={this.onPressLogin}
            disabled={user.fetching || !login.isValid}
          />
          <Text center tx='loginScreen.forgot' />
          <Stretchy />
          <TextWithSmallGap tx='loginScreen.noAccount' />
          <Button.Clear tx='loginScreen.signup' onPress={() => navigationStore.navigateTo('signup')}/>
          <KeyboardSpacer />
      </StaticLayout>
    )
  }
}
