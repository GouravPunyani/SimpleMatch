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

export type ShippingAddressScreenProps = {
};

@translator
@inject('userStore', 'navigationStore')
export class ShippingAddressScreen extends Component {
  static propTypes: ShippingAddressScreen;

  render () {
    const { navigationStore } = this.props
    return (
      // TODO: This isn't connect to the account. The information doesn't update.
      <ScrollingLayout>
          <Form>
            <View style={{flexDirection: 'row'}}><View style={{flex: 1}}>
            <TextField
              labelTx='account.firstNameLabel'
              value='Matthieu'/* <-- Will need to be taken from account*/
            /></View><Breaky />
            <View style={{flex: 1}}><TextField
              labelTx='account.lastNameLabel'
              value='Rey'/* <-- Will need to be taken from account*/
            /></View></View>
            <TextField
              labelTx='account.address1Label'
              value='1421 5th Street'/* <-- Will need to be taken from account*/
            />
            <TextField
              labelTx='account.address2Label'
              value=''/* <-- Will need to be taken from account*/
            />
            <View style={{flexDirection: 'row'}}><View style={{flex: 1}}>
            <TextField
              labelTx='account.cityLabel'
              value='Santa Monica'/* <-- Will need to be taken from account*/
            /></View><Breaky />
            <View style={{flex: 1}}><TextField
              labelTx='account.stateLabel'
              value='CA'/* <-- Will need to be taken from account*/
            /></View></View>
            <View style={{flexDirection: 'row'}}><View style={{flex: 1}}>
            <TextField
              labelTx='account.zipCodeLabel'
              value='90401'/* <-- Will need to be taken from account*/
            /></View><Breaky />
            <View style={{flex: 1}}><TextField
              labelTx='account.phoneNumberLabel'
              value='123.456.7890'/* <-- Will need to be taken from account*/
            /></View></View>
          </Form><Stretchy/>
          <Button
            tx='account.saveAddress'
            //onPress={this.onPressLogin}
            //disabled={user.fetching || !login.isValid}
          />
          <KeyboardSpacer />
      </ScrollingLayout>
    )
  }
}
