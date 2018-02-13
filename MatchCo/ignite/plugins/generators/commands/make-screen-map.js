// @cliDescription Rebuilds the ui registry.
module.exports = async function (context) {
  const { print, filesystem, strings, template, system } = context
  const { pascalCase, camelCase } = strings

  const screensDir = `src/ui/screens`

  const dirs = filesystem.cwd(screensDir).find({
    matching: '*',
    files: false,
    recursive: false,
    directories: true
  }) || []

  // create the screen
  const screens = dirs.map(dir => {
    const hasContainer = filesystem.exists(`./src/ui/screens/${dir}/${dir}.container.js`)
    return {
      name: camelCase(dir.replace('-screen', '')),
      component: pascalCase(hasContainer ? `${dir}Container` : dir)
    }
  })

  // generate the screen map
  const screenMapTarget = 'src/ui/navigation/screen-map.js'
  template.generate({
    template: 'screen-map.js.ejs',
    target: screenMapTarget,
    props: { screens }
  })
  system.exec(`./node_modules/.bin/prettier --write ${screenMapTarget}`)
  system.exec(`./node_modules/.bin/standard --fix ${screenMapTarget}`)

  // // generate the navigation actions
  // const navigationActionsTarget = 'src/data/navigation/navigation.actions.js'
  // template.generate({
  //   template: 'navigation.actions.js.ejs',
  //   target: navigationActionsTarget,
  //   props: { screens }
  // })
  // system.exec(`./node_modules/.bin/prettier --write ${navigationActionsTarget}`)
  // system.exec(`./node_modules/.bin/standard --fix ${navigationActionsTarget}`)

  print.info(`  ${print.colors.success(print.checkmark)} screen map rebuilt`)
}
