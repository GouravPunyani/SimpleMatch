// @flow
import React, { Component } from 'react'
import { Keyboard, Alert, View, Dimensions } from 'react-native'
import {
  Button,
  ButtonPanel,
  ScrollingLayout,
  Text,
  TextField,
  Form,
  KeyboardSpacer
} from '../..'
import { spacing } from '../../theme'
import glam from 'glamorous-native'
//import { Login } from '../../../entities/login'
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

const Breaky = glam.view({ width: width*.03 })

const Stretchy = glam.view({ height: height*.06 })

export type SignupScreenProps = {
};

@translator
@inject('userStore', 'navigationStore')
export default class SignupScreen extends Component {
  static propTypes: SignupScreen;

  render () {
    const { navigationStore } = this.props
    return (
      <ScrollingLayout>
          <Button.Facebook tx='signupScreen.signupWithFacebook' />
          <OrText tx='common.or' />
          <Form>
          	<View style={{flexDirection: 'row'}}><View style={{flex: 1}}>
          	<TextField
              labelTx='signupScreen.firstNameLabel'
            /></View><Breaky />
            <View style={{flex: 1}}><TextField
              labelTx='signupScreen.lastNameLabel'
            /></View></View>
            <TextField
              labelTx='signupScreen.emailLabel'
            />
            <TextField
              labelTx='signupScreen.passwordLabel'
            />
            <TextField
              labelTx='signupScreen.rePasswordLabel'
            />
          </Form>
          <Button
            tx='signupScreen.signup'
            //onPress={this.onPressLogin}
            //disabled={user.fetching || !login.isValid}
          />
          <Stretchy />
          <TextWithSmallGap tx='signupScreen.hasAccount' />
          <Button.Clear tx='signupScreen.login' onPress={() => navigationStore.navigateTo('login')}/>
          <KeyboardSpacer />
      </ScrollingLayout>
    )
  }
}
