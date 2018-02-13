// @flow

import type { NavigationStore } from '../../../stores/navigation-store'
import type { UserStore } from '../../../stores/user-store'
import React, { Component } from 'react'
import { inject, observer } from 'mobx-react'
import { StyleSheet, ScrollView } from 'react-native'
import { DrawerButton } from '../..'
import { bind } from 'decko'
import { translator } from '../../../services/translate/translator'

export type Props = {
  userStore: UserStore,
  navigationStore: NavigationStore,
  translate: (key: string) => string
};

@translator
@inject('userStore', 'navigationStore')
@observer
export class DrawerContent extends Component<*, Props, *> {

  /**
   * Kick off the logout flow.
   */
  @bind
  async logout () {
    const { userStore, navigationStore } = this.props
    navigationStore.navigateTo('dashboardStack')
    userStore.logout()
  }

  render () {
    const { userStore, navigationStore, translate } = this.props
    const navigateTo = routeName => () => navigationStore.navigateTo(routeName)

    return (
      <ScrollView style={styles.scroller}>
        <DrawerButton text={translate('drawer.newScan')} onPress={navigateTo('scanStack')} />
        <DrawerButton text={translate('drawer.product')} onPress={navigateTo('productStack')} />
        <DrawerButton text={translate('drawer.account')} onPress={navigateTo('accountStack')} />
        <DrawerButton
          text={translate('drawer.share')}
          onPress={navigateTo('referralStack')}
        />
        <DrawerButton text={translate('drawer.howToScan')} onPress={navigateTo('howToStack')} />
        <DrawerButton text={translate('drawer.faqs')} onPress={navigateTo('faqStack')} />
        <DrawerButton text={translate('drawer.contact')} onPress={navigateTo('contactStack')} />
        {userStore.isLoggedIn
          ? <DrawerButton text={translate('drawer.logout')} onPress={this.logout} />
          : <DrawerButton text={translate('drawer.login')} onPress={navigateTo('loginStack')} />}

      </ScrollView>
    )
  }
}

const styles = StyleSheet.create({
  scroller: {
    backgroundColor: '#2d2d2d',
    flex: 1,
    paddingTop: 100,
    paddingLeft: 20
  }
})
