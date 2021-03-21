const chalk = require('chalk');
const semver = require('semver');
const BaseGenerator = require('generator-jhipster/generators/generator-base');
const jhipsterConstants = require('generator-jhipster/generators/generator-constants');
const packagejs = require('../../package.json');

module.exports = class extends BaseGenerator {
    get initializing() {
        return {
            init(args) {
                if (args === 'default') {
                    // do something when argument is 'default'
                    this.message = 'default message';
                }
            },
            readConfig() {
                this.jhipsterAppConfig = this.getAllJhipsterConfig();
                if (!this.jhipsterAppConfig) {
                    this.error('Cannot read .yo-rc.json');
                }
            },
            displayLogo() {
                // it's here to show that you can use functions from generator-jhipster
                // this function is in: generator-jhipster/generators/generator-base.js
                this.printJHipsterLogo();

                // Have Yeoman greet the user.
                this.log(
                    `\nWelcome to the ${chalk.bold.yellow('JHipster fullcalendar')} generator! ${chalk.yellow(`v${packagejs.version}\n`)}`
                );
            },
            checkJhipster() {
                const currentJhipsterVersion = this.jhipsterAppConfig.jhipsterVersion;
                const minimumJhipsterVersion = packagejs.dependencies['generator-jhipster'];
                if (!semver.satisfies(currentJhipsterVersion, minimumJhipsterVersion)) {
                    this.warning(
                        `\nYour generated project used an old JHipster version (${currentJhipsterVersion})... you need at least (${minimumJhipsterVersion})\n`
                    );
                }
            }
        };
    }

    prompting() {
        const prompts = [
            {
                when: () => typeof this.message === 'undefined',
                type: 'input',
                name: 'message',
                message: 'Please put something',
                default: 'hello world!'
            }
        ];

        const done = this.async();
        this.prompt(prompts).then(answers => {
            this.promptAnswers = answers;
            // To access props answers use this.promptAnswers.someOption;
            done();
        });
    }

    writing() {
        // read config from .yo-rc.json
        this.baseName = this.jhipsterAppConfig.baseName;
        this.packageName = this.jhipsterAppConfig.packageName;
        this.packageFolder = this.jhipsterAppConfig.packageFolder;
        this.clientFramework = this.jhipsterAppConfig.clientFramework;
        this.clientPackageManager = this.jhipsterAppConfig.clientPackageManager;
        this.buildTool = this.jhipsterAppConfig.buildTool;

        // use function in generator-base.js from generator-jhipster
        this.angularAppName = this.getAngularAppName();

        // use constants from generator-constants.js
        const javaDir = `${jhipsterConstants.SERVER_MAIN_SRC_DIR + this.packageFolder}/`;
        const resourceDir = jhipsterConstants.SERVER_MAIN_RES_DIR;
        const webappDir = jhipsterConstants.CLIENT_MAIN_SRC_DIR;

        // variable from questions
        if (typeof this.message === 'undefined') {
            this.message = this.promptAnswers.message;
        }

        // needles variables for imports and package in file;
        const needle_import = '//<--! import -->';
        const needle_sharedmodule = '//<--! sharedmodule -->';
        const needle_package = '//<--! package -->';
        const needle_liquibase = '<!-- jhipster-needle-liquibase-add-changelog - JHipster will add liquibase changelogs here -->';
        const needle_liquibase_constraints =
            '<!-- jhipster-needle-liquibase-add-constraints-changelog - JHipster will add liquibase constraints changelogs here -->';

        // show all variables
        this.log('\n--- some config read from config ---');
        this.log(`baseName=${this.baseName}`);
        this.log(`packageName=${this.packageName}`);
        this.log(`clientFramework=${this.clientFramework}`);
        this.log(`clientPackageManager=${this.clientPackageManager}`);
        this.log(`buildTool=${this.buildTool}`);

        this.log('\n--- some function ---');
        this.log(`angularAppName=${this.angularAppName}`);

        this.log('\n--- some const ---');
        this.log(`javaDir=${javaDir}`);
        this.log(`resourceDir=${resourceDir}`);
        this.log(`webappDir=${webappDir}`);

        this.log('\n--- variables from questions ---');
        this.log(`message=${this.message}`);
        this.log('------\n');

        if (this.clientFramework === 'react') {
            // No front-end change.
        }
        if (this.clientFramework === 'angularX') {
            // app/fullcalendar
            this.fs.copy(this.templatePath('src/main/webapp/app/fullcalendar'), this.destinationPath(webappDir + 'app/fullcalendar'));

            // app/entities/fullcalendar
            this.fs.copy(
                this.templatePath('src/main/webapp/app/entities/fullcalendar'),
                this.destinationPath(webappDir + 'app/entities/fullcalendar')
            );

            // app/entities/fullcalendar-event
            this.fs.copy(
                this.templatePath('src/main/webapp/app/entities/fullcalendar-event'),
                this.destinationPath(webappDir + 'app/entities/fullcalendar-event')
            );

            // app/entities/fullcalendar-provider
            this.fs.copy(
                this.templatePath('src/main/webapp/app/entities/fullcalendar-provider'),
                this.destinationPath(webappDir + 'app/entities/fullcalendar-provider')
            );

            // app/shared/model/fullcalendar.model.ts
            this.fs.copy(
                this.templatePath('src/main/webapp/app/shared/model/fullcalendar.model.ts'),
                this.destinationPath(webappDir + 'app/shared/model/fullcalendar.model.ts')
            );

            // app/shared/model/fullcalendar-event.model.ts
            this.fs.copy(
                this.templatePath('src/main/webapp/app/shared/model/fullcalendar-event.model.ts'),
                this.destinationPath(webappDir + 'app/shared/model/fullcalendar-event.model.ts')
            );

            // app/shared/model/fullcalendar-provider.model.ts
            this.fs.copy(
                this.templatePath('src/main/webapp/app/shared/model/fullcalendar-provider.model.ts'),
                this.destinationPath(webappDir + 'app/shared/model/fullcalendar-provider.model.ts')
            );

            // app/shared/model/fullcalendar-provider.model.ts
            this.fs.copy(
                this.templatePath('src/main/webapp/app/shared/model/fullcalendar-provider.model.ts'),
                this.destinationPath(webappDir + 'app/shared/model/fullcalendar-provider.model.ts')
            );

            // app/shared/model/enumerations
            this.fs.copy(
                this.templatePath('src/main/webapp/app/shared/model/enumerations/type-fullcalendar-event-status.model.ts'),
                this.destinationPath(webappDir + 'app/shared/model/enumerations/type-fullcalendar-event-status.model.ts')
            );
            this.fs.copy(
                this.templatePath('src/main/webapp/app/shared/model/enumerations/type-fullcalendar-provider.model.ts'),
                this.destinationPath(webappDir + 'app/shared/model/enumerations/type-fullcalendar-provider.model.ts')
            );

            // add entities to menu
            this.addEntityToMenu('calendar', false, this.clientFramework);
            this.addEntityToMenu('calendar-event', false, this.clientFramework);
            this.addEntityToMenu('calendar-provider', false, this.clientFramework);

            // add entity to module
            this.rewriteFile(
                webappDir + 'app/entities/entity.module.ts',
                '/* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */',
                "{path: 'fullcalendar-event', loadChildren: () => import('./fullcalendar-event/calendar-event.module').then(m => m.FullCalendarCalendarEventModule)},\n {path: 'fullcalendar', loadChildren: () => import('./fullcalendar/calendar.module').then(m => m.FullCalendarCalendarModule)},\n {path: 'fullcalendar-provider', loadChildren: () => import('./fullcalendar-provider/calendar-provider.module').then(m => m.FullCalendarCalendarProviderModule)},"
            );

            // app/entities/photo-photo/photo-photo.module.ts
            var baseNameUpperCase = this.baseName;
            baseNameUpperCase = baseNameUpperCase.cleanup();

            // SharedModule
            this.rewriteFile(
                webappDir + 'app/fullcalendar/calendars.module.ts',
                needle_import,
                'import { ' + baseNameUpperCase + "SharedModule } from 'app/shared/shared.module';"
            );
            this.rewriteFile(
                webappDir + 'app/fullcalendar/calendars.module.ts',
                needle_sharedmodule,
                'imports: [' + baseNameUpperCase + 'SharedModule, FullCalendarModule, RouterModule.forChild([CALENDARS_ROUTE])],'
            );

            this.rewriteFile(
                webappDir + 'app/entities/fullcalendar/calendar.module.ts',
                needle_import,
                'import { ' + baseNameUpperCase + "SharedModule } from 'app/shared/shared.module';"
            );
            this.rewriteFile(
                webappDir + 'app/entities/fullcalendar/calendar.module.ts',
                needle_sharedmodule,
                'imports: [' + baseNameUpperCase + 'SharedModule, RouterModule.forChild(calendarRoute)],'
            );

            this.rewriteFile(
                webappDir + 'app/entities/fullcalendar-event/calendar-event.module.ts',
                needle_import,
                'import { ' + baseNameUpperCase + "SharedModule } from 'app/shared/shared.module';"
            );
            this.rewriteFile(
                webappDir + 'app/entities/fullcalendar-event/calendar-event.module.ts',
                needle_sharedmodule,
                'imports: [' + baseNameUpperCase + 'SharedModule, RouterModule.forChild(calendarEventRoute)],'
            );

            this.rewriteFile(
                webappDir + 'app/entities/fullcalendar-provider/calendar-provider.module.ts',
                needle_import,
                'import { ' + baseNameUpperCase + "SharedModule } from 'app/shared/shared.module';"
            );
            this.rewriteFile(
                webappDir + 'app/entities/fullcalendar-provider/calendar-provider.module.ts',
                needle_sharedmodule,
                'imports: [' + baseNameUpperCase + 'SharedModule, RouterModule.forChild(calendarProviderRoute)],'
            );
        }
        if (this.buildTool === 'maven') {
            // No Pom.xml dependencies
        }
        if (this.buildTool === 'gradle') {
        }

        // domain/Calendar.java
        this.template('src/main/java/package/domain/Calendar.java', javaDir + 'domain/Calendar.java');
        this.rewriteFile(javaDir + 'domain/Calendar.java', needle_package, 'package ' + this.packageName + '.domain;');

        // domain/CalendarEvent.java
        this.template('src/main/java/package/domain/CalendarEvent.java', javaDir + 'domain/CalendarEvent.java');
        this.rewriteFile(javaDir + 'domain/CalendarEvent.java', needle_package, 'package ' + this.packageName + '.domain;');
        this.rewriteFile(
            javaDir + 'domain/CalendarEvent.java',
            needle_import,
            'import ' + this.packageName + '.domain.enumeration.TypeCalendarEventStatus;'
        );

        // domain/CalendarProvider.java
        this.template('src/main/java/package/domain/CalendarProvider.java', javaDir + 'domain/CalendarProvider.java');
        this.rewriteFile(javaDir + 'domain/CalendarProvider.java', needle_package, 'package ' + this.packageName + '.domain;');
        this.rewriteFile(
            javaDir + 'domain/CalendarProvider.java',
            needle_import,
            'import ' + this.packageName + '.domain.enumeration.TypeCalendarProvider;'
        );

        // domain/enumeration/TypeCalendarEventStatus.java
        this.template(
            'src/main/java/package/domain/enumeration/TypeCalendarEventStatus.java',
            javaDir + 'domain/enumeration/TypeCalendarEventStatus.java'
        );
        this.rewriteFile(
            javaDir + 'domain/enumeration/TypeCalendarEventStatus.java',
            needle_package,
            'package ' + this.packageName + '.domain.enumeration;'
        );

        // domain/enumeration/TypeCalendarProvider.java
        this.template(
            'src/main/java/package/domain/enumeration/TypeCalendarProvider.java',
            javaDir + 'domain/enumeration/TypeCalendarProvider.java'
        );
        this.rewriteFile(
            javaDir + 'domain/enumeration/TypeCalendarProvider.java',
            needle_package,
            'package ' + this.packageName + '.domain.enumeration;'
        );

        // repository/CalendarEventRepository.java
        this.template('src/main/java/package/repository/CalendarEventRepository.java', javaDir + 'repository/CalendarEventRepository.java');
        this.rewriteFile(
            javaDir + 'repository/CalendarEventRepository.java',
            needle_package,
            'package ' + this.packageName + '.repository;'
        );
        this.rewriteFile(
            javaDir + 'repository/CalendarEventRepository.java',
            needle_import,
            'import ' + this.packageName + '.domain.CalendarEvent;'
        );

        // repository/CalendarProviderRepository.java
        this.template(
            'src/main/java/package/repository/CalendarProviderRepository.java',
            javaDir + 'repository/CalendarProviderRepository.java'
        );
        this.rewriteFile(
            javaDir + 'repository/CalendarProviderRepository.java',
            needle_package,
            'package ' + this.packageName + '.repository;'
        );
        this.rewriteFile(
            javaDir + 'repository/CalendarProviderRepository.java',
            needle_import,
            'import ' + this.packageName + '.domain.CalendarProvider;'
        );

        // repository/CalendarRepository.java
        this.template('src/main/java/package/repository/CalendarRepository.java', javaDir + 'repository/CalendarRepository.java');
        this.rewriteFile(javaDir + 'repository/CalendarRepository.java', needle_package, 'package ' + this.packageName + '.repository;');
        this.rewriteFile(javaDir + 'repository/CalendarRepository.java', needle_import, 'import ' + this.packageName + '.domain.Calendar;');

        // repository/search/CalendarSearchRepository.java
        this.template(
            'src/main/java/package/repository/search/CalendarSearchRepository.java',
            javaDir + 'repository/search/CalendarSearchRepository.java'
        );
        this.rewriteFile(
            javaDir + 'repository/search/CalendarSearchRepository.java',
            needle_package,
            'package ' + this.packageName + '.repository.search;'
        );
        this.rewriteFile(
            javaDir + 'repository/search/CalendarSearchRepository.java',
            needle_import,
            'import ' + this.packageName + '.domain.Calendar;'
        );

        // repository/search/CalendarEventSearchRepository.java
        this.template(
            'src/main/java/package/repository/search/CalendarEventSearchRepository.java',
            javaDir + 'repository/search/CalendarEventSearchRepository.java'
        );
        this.rewriteFile(
            javaDir + 'repository/search/CalendarEventSearchRepository.java',
            needle_package,
            'package ' + this.packageName + '.repository.search;'
        );
        this.rewriteFile(
            javaDir + 'repository/search/CalendarEventSearchRepository.java',
            needle_import,
            'import ' + this.packageName + '.domain.CalendarEvent;'
        );

        // repository/search/CalendarProviderSearchRepository.java
        this.template(
            'src/main/java/package/repository/search/CalendarProviderSearchRepository.java',
            javaDir + 'repository/search/CalendarProviderSearchRepository.java'
        );
        this.rewriteFile(
            javaDir + 'repository/search/CalendarProviderSearchRepository.java',
            needle_package,
            'package ' + this.packageName + '.repository.search;'
        );
        this.rewriteFile(
            javaDir + 'repository/search/CalendarProviderSearchRepository.java',
            needle_import,
            'import ' + this.packageName + '.domain.CalendarProvider;'
        );

        // service/CalendarEventQueryService.java
        this.template('src/main/java/package/service/CalendarEventQueryService.java', javaDir + 'service/CalendarEventQueryService.java');
        this.rewriteFile(javaDir + 'service/CalendarEventQueryService.java', needle_package, 'package ' + this.packageName + '.service;');
        this.rewriteFile(
            javaDir + 'service/CalendarEventQueryService.java',
            needle_import,
            'import ' + this.packageName + '.domain.CalendarEvent;'
        );
        this.rewriteFile(javaDir + 'service/CalendarEventQueryService.java', needle_import, 'import ' + this.packageName + '.domain.*;');
        this.rewriteFile(
            javaDir + 'service/CalendarEventQueryService.java',
            needle_import,
            'import ' + this.packageName + '.repository.CalendarEventRepository;'
        );
        this.rewriteFile(
            javaDir + 'service/CalendarEventQueryService.java',
            needle_import,
            'import ' + this.packageName + '.repository.search.CalendarEventSearchRepository;'
        );
        this.rewriteFile(
            javaDir + 'service/CalendarEventQueryService.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.CalendarEventCriteria;'
        );
        this.rewriteFile(
            javaDir + 'service/CalendarEventQueryService.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.CalendarEventDTO;'
        );
        this.rewriteFile(
            javaDir + 'service/CalendarEventQueryService.java',
            needle_import,
            'import ' + this.packageName + '.service.mapper.CalendarEventMapper;'
        );

        // service/CalendarEventService.java
        this.template('src/main/java/package/service/CalendarEventService.java', javaDir + 'service/CalendarEventService.java');
        this.rewriteFile(javaDir + 'service/CalendarEventService.java', needle_package, 'package ' + this.packageName + '.service;');
        this.rewriteFile(
            javaDir + 'service/CalendarEventService.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.CalendarEventDTO;'
        );

        // service/CalendarProviderQueryService.java
        this.template(
            'src/main/java/package/service/CalendarProviderQueryService.java',
            javaDir + 'service/CalendarProviderQueryService.java'
        );
        this.rewriteFile(
            javaDir + 'service/CalendarProviderQueryService.java',
            needle_package,
            'package ' + this.packageName + '.service;'
        );
        this.rewriteFile(
            javaDir + 'service/CalendarProviderQueryService.java',
            needle_import,
            'import ' + this.packageName + '.domain.CalendarProvider;'
        );
        this.rewriteFile(javaDir + 'service/CalendarProviderQueryService.java', needle_import, 'import ' + this.packageName + '.domain.*;');
        this.rewriteFile(
            javaDir + 'service/CalendarProviderQueryService.java',
            needle_import,
            'import ' + this.packageName + '.repository.CalendarProviderRepository;'
        );
        this.rewriteFile(
            javaDir + 'service/CalendarProviderQueryService.java',
            needle_import,
            'import ' + this.packageName + '.repository.search.CalendarProviderSearchRepository;'
        );
        this.rewriteFile(
            javaDir + 'service/CalendarProviderQueryService.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.CalendarProviderCriteria;'
        );
        this.rewriteFile(
            javaDir + 'service/CalendarProviderQueryService.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.CalendarProviderDTO;'
        );
        this.rewriteFile(
            javaDir + 'service/CalendarProviderQueryService.java',
            needle_import,
            'import ' + this.packageName + '.service.mapper.CalendarProviderMapper;'
        );

        // service/CalendarProviderService.java
        this.template('src/main/java/package/service/CalendarProviderService.java', javaDir + 'service/CalendarProviderService.java');
        this.rewriteFile(javaDir + 'service/CalendarProviderService.java', needle_package, 'package ' + this.packageName + '.service;');
        this.rewriteFile(
            javaDir + 'service/CalendarProviderService.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.CalendarProviderDTO;'
        );

        // service/CalendarQueryService.java
        this.template('src/main/java/package/service/CalendarQueryService.java', javaDir + 'service/CalendarQueryService.java');
        this.rewriteFile(javaDir + 'service/CalendarQueryService.java', needle_package, 'package ' + this.packageName + '.service;');
        this.rewriteFile(javaDir + 'service/CalendarQueryService.java', needle_import, 'import ' + this.packageName + '.domain.Calendar;');
        this.rewriteFile(javaDir + 'service/CalendarQueryService.java', needle_import, 'import ' + this.packageName + '.domain.*;');
        this.rewriteFile(
            javaDir + 'service/CalendarQueryService.java',
            needle_import,
            'import ' + this.packageName + '.repository.CalendarRepository;'
        );
        this.rewriteFile(
            javaDir + 'service/CalendarQueryService.java',
            needle_import,
            'import ' + this.packageName + '.repository.search.CalendarSearchRepository;'
        );
        this.rewriteFile(
            javaDir + 'service/CalendarQueryService.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.CalendarCriteria;'
        );
        this.rewriteFile(
            javaDir + 'service/CalendarQueryService.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.CalendarDTO;'
        );
        this.rewriteFile(
            javaDir + 'service/CalendarQueryService.java',
            needle_import,
            'import ' + this.packageName + '.service.mapper.CalendarMapper;'
        );

        // service/CalendarService.java
        this.template('src/main/java/package/service/CalendarService.java', javaDir + 'service/CalendarService.java');
        this.rewriteFile(javaDir + 'service/CalendarService.java', needle_package, 'package ' + this.packageName + '.service;');
        this.rewriteFile(
            javaDir + 'service/CalendarService.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.CalendarDTO;'
        );

        // service/dto/CalendarCriteria.java
        this.template('src/main/java/package/service/dto/CalendarCriteria.java', javaDir + 'service/dto/CalendarCriteria.java');
        this.rewriteFile(javaDir + 'service/dto/CalendarCriteria.java', needle_package, 'package ' + this.packageName + '.service.dto;');

        // service/dto/CalendarDTO.java
        this.template('src/main/java/package/service/dto/CalendarDTO.java', javaDir + 'service/dto/CalendarDTO.java');
        this.rewriteFile(javaDir + 'service/dto/CalendarDTO.java', needle_package, 'package ' + this.packageName + '.service.dto;');

        // service/dto/CalendarEventCriteria.java
        this.template('src/main/java/package/service/dto/CalendarEventCriteria.java', javaDir + 'service/dto/CalendarEventCriteria.java');
        this.rewriteFile(
            javaDir + 'service/dto/CalendarEventCriteria.java',
            needle_package,
            'package ' + this.packageName + '.service.dto;'
        );
        this.rewriteFile(
            javaDir + 'service/dto/CalendarEventCriteria.java',
            needle_import,
            'import ' + this.packageName + '.domain.enumeration.TypeCalendarEventStatus;'
        );

        // service/dto/CalendarEventDTO.java
        this.template('src/main/java/package/service/dto/CalendarEventDTO.java', javaDir + 'service/dto/CalendarEventDTO.java');
        this.rewriteFile(javaDir + 'service/dto/CalendarEventDTO.java', needle_package, 'package ' + this.packageName + '.service.dto;');
        this.rewriteFile(
            javaDir + 'service/dto/CalendarEventDTO.java',
            needle_import,
            'import ' + this.packageName + '.domain.enumeration.TypeCalendarEventStatus;'
        );

        // service/dto/CalendarProviderCriteria.java
        this.template(
            'src/main/java/package/service/dto/CalendarProviderCriteria.java',
            javaDir + 'service/dto/CalendarProviderCriteria.java'
        );
        this.rewriteFile(
            javaDir + 'service/dto/CalendarProviderCriteria.java',
            needle_package,
            'package ' + this.packageName + '.service.dto;'
        );
        this.rewriteFile(
            javaDir + 'service/dto/CalendarProviderCriteria.java',
            needle_import,
            'import ' + this.packageName + '.domain.enumeration.TypeCalendarProvider;'
        );

        // service/dto/CalendarProviderDTO.java
        this.template('src/main/java/package/service/dto/CalendarProviderDTO.java', javaDir + 'service/dto/CalendarProviderDTO.java');
        this.rewriteFile(javaDir + 'service/dto/CalendarProviderDTO.java', needle_package, 'package ' + this.packageName + '.service.dto;');
        this.rewriteFile(
            javaDir + 'service/dto/CalendarProviderDTO.java',
            needle_import,
            'import ' + this.packageName + '.domain.enumeration.TypeCalendarProvider;'
        );

        // service/impl/CalendarEventServiceImpl.java
        this.template(
            'src/main/java/package/service/impl/CalendarEventServiceImpl.java',
            javaDir + 'service/impl/CalendarEventServiceImpl.java'
        );
        this.rewriteFile(
            javaDir + 'service/impl/CalendarEventServiceImpl.java',
            needle_package,
            'package ' + this.packageName + '.service.impl;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/CalendarEventServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.service.CalendarEventService;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/CalendarEventServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.domain.CalendarEvent;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/CalendarEventServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.repository.CalendarEventRepository;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/CalendarEventServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.repository.search.CalendarEventSearchRepository;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/CalendarEventServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.CalendarEventDTO;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/CalendarEventServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.service.mapper.CalendarEventMapper;'
        );

        // service/impl/CalendarProviderServiceImpl.java
        this.template(
            'src/main/java/package/service/impl/CalendarProviderServiceImpl.java',
            javaDir + 'service/impl/CalendarProviderServiceImpl.java'
        );
        this.rewriteFile(
            javaDir + 'service/impl/CalendarProviderServiceImpl.java',
            needle_package,
            'package ' + this.packageName + '.service.impl;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/CalendarProviderServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.service.CalendarProviderService;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/CalendarProviderServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.domain.CalendarProvider;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/CalendarProviderServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.repository.CalendarProviderRepository;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/CalendarProviderServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.repository.search.CalendarProviderSearchRepository;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/CalendarProviderServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.CalendarProviderDTO;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/CalendarProviderServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.service.mapper.CalendarProviderMapper;'
        );

        // service/impl/CalendarServiceImpl.java
        this.template('src/main/java/package/service/impl/CalendarServiceImpl.java', javaDir + 'service/impl/CalendarServiceImpl.java');
        this.rewriteFile(
            javaDir + 'service/impl/CalendarServiceImpl.java',
            needle_package,
            'package ' + this.packageName + '.service.impl;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/CalendarServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.service.CalendarService;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/CalendarServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.domain.Calendar;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/CalendarServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.repository.CalendarRepository;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/CalendarServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.repository.search.CalendarSearchRepository;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/CalendarServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.CalendarDTO;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/CalendarServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.service.mapper.CalendarMapper;'
        );

        // service/mapper/CalendarEventMapper.java
        this.template('src/main/java/package/service/mapper/CalendarEventMapper.java', javaDir + 'service/mapper/CalendarEventMapper.java');
        this.rewriteFile(
            javaDir + 'service/mapper/CalendarEventMapper.java',
            needle_package,
            'package ' + this.packageName + '.service.mapper;'
        );
        this.rewriteFile(javaDir + 'service/mapper/CalendarEventMapper.java', needle_import, 'import ' + this.packageName + '.domain.*;');
        this.rewriteFile(
            javaDir + 'service/mapper/CalendarEventMapper.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.CalendarEventDTO;'
        );

        // service/mapper/CalendarMapper.java
        this.template('src/main/java/package/service/mapper/CalendarMapper.java', javaDir + 'service/mapper/CalendarMapper.java');
        this.rewriteFile(
            javaDir + 'service/mapper/CalendarMapper.java',
            needle_package,
            'package ' + this.packageName + '.service.mapper;'
        );
        this.rewriteFile(javaDir + 'service/mapper/CalendarMapper.java', needle_import, 'import ' + this.packageName + '.domain.*;');
        this.rewriteFile(
            javaDir + 'service/mapper/CalendarMapper.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.CalendarDTO;'
        );

        // service/mapper/CalendarProviderMapper.java
        this.template(
            'src/main/java/package/service/mapper/CalendarProviderMapper.java',
            javaDir + 'service/mapper/CalendarProviderMapper.java'
        );
        this.rewriteFile(
            javaDir + 'service/mapper/CalendarProviderMapper.java',
            needle_package,
            'package ' + this.packageName + '.service.mapper;'
        );
        this.rewriteFile(
            javaDir + 'service/mapper/CalendarProviderMapper.java',
            needle_import,
            'import ' + this.packageName + '.domain.*;'
        );
        this.rewriteFile(
            javaDir + 'service/mapper/CalendarProviderMapper.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.CalendarProviderDTO;'
        );

        // service/mapper/EntityMapper.java
        this.template('src/main/java/package/service/mapper/EntityMapper.java', javaDir + 'service/mapper/EntityMapper.java');
        this.rewriteFile(javaDir + 'service/mapper/EntityMapper.java', needle_package, 'package ' + this.packageName + '.service.mapper;');

        // web/rest/CalendarEventResource.java
        this.template('src/main/java/package/web/rest/CalendarEventResource.java', javaDir + 'web/rest/CalendarEventResource.java');
        this.rewriteFile(javaDir + 'web/rest/CalendarEventResource.java', needle_package, 'package ' + this.packageName + '.web.rest;');
        this.rewriteFile(
            javaDir + 'web/rest/CalendarEventResource.java',
            needle_import,
            'import ' + this.packageName + '.service.CalendarEventService;'
        );
        this.rewriteFile(
            javaDir + 'web/rest/CalendarEventResource.java',
            needle_import,
            'import ' + this.packageName + '.web.rest.errors.BadRequestAlertException;'
        );
        this.rewriteFile(
            javaDir + 'web/rest/CalendarEventResource.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.CalendarEventDTO;'
        );
        this.rewriteFile(
            javaDir + 'web/rest/CalendarEventResource.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.CalendarEventCriteria;'
        );
        this.rewriteFile(
            javaDir + 'web/rest/CalendarEventResource.java',
            needle_import,
            'import ' + this.packageName + '.service.CalendarEventQueryService;'
        );

        // web/rest/CalendarProviderResource.java
        this.template('src/main/java/package/web/rest/CalendarProviderResource.java', javaDir + 'web/rest/CalendarProviderResource.java');
        this.rewriteFile(javaDir + 'web/rest/CalendarProviderResource.java', needle_package, 'package ' + this.packageName + '.web.rest;');
        this.rewriteFile(
            javaDir + 'web/rest/CalendarProviderResource.java',
            needle_import,
            'import ' + this.packageName + '.service.CalendarProviderService;'
        );
        this.rewriteFile(
            javaDir + 'web/rest/CalendarProviderResource.java',
            needle_import,
            'import ' + this.packageName + '.web.rest.errors.BadRequestAlertException;'
        );
        this.rewriteFile(
            javaDir + 'web/rest/CalendarProviderResource.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.CalendarProviderDTO;'
        );
        this.rewriteFile(
            javaDir + 'web/rest/CalendarProviderResource.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.CalendarProviderCriteria;'
        );
        this.rewriteFile(
            javaDir + 'web/rest/CalendarProviderResource.java',
            needle_import,
            'import ' + this.packageName + '.service.CalendarProviderQueryService;'
        );

        // web/rest/CalendarResource.java
        this.template('src/main/java/package/web/rest/CalendarResource.java', javaDir + 'web/rest/CalendarResource.java');
        this.rewriteFile(javaDir + 'web/rest/CalendarResource.java', needle_package, 'package ' + this.packageName + '.web.rest;');
        this.rewriteFile(
            javaDir + 'web/rest/CalendarResource.java',
            needle_import,
            'import ' + this.packageName + '.service.CalendarService;'
        );
        this.rewriteFile(
            javaDir + 'web/rest/CalendarResource.java',
            needle_import,
            'import ' + this.packageName + '.web.rest.errors.BadRequestAlertException;'
        );
        this.rewriteFile(
            javaDir + 'web/rest/CalendarResource.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.CalendarDTO;'
        );
        this.rewriteFile(
            javaDir + 'web/rest/CalendarResource.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.CalendarCriteria;'
        );
        this.rewriteFile(
            javaDir + 'web/rest/CalendarResource.java',
            needle_import,
            'import ' + this.packageName + '.service.CalendarQueryService;'
        );

        //
        // Files added in resources package
        //
        this.template(
            'src/main/resources/config/liquibase/fake-data/blob/hipster.png',
            resourceDir + 'config/liquibase/fake-data/blob/hipster.png'
        );
        this.template(
            'src/main/resources/config/liquibase/fake-data/blob/hipster.txt',
            resourceDir + 'config/liquibase/fake-data/blob/hipster.txt'
        );

        // Calendar
        this.template(
            'src/main/resources/config/liquibase/fake-data/calendar.csv',
            resourceDir + 'config/liquibase/fake-data/calendar.csv'
        );
        this.template(
            'src/main/resources/config/liquibase/changelog/20200211162600_added_entity_constraints_Calendar.xml',
            resourceDir + 'config/liquibase/changelog/20200211162600_added_entity_constraints_Calendar.xml'
        );
        this.template(
            'src/main/resources/config/liquibase/changelog/20200211162600_added_entity_Calendar.xml',
            resourceDir + 'config/liquibase/changelog/20200211162600_added_entity_Calendar.xml'
        );

        // CalendarEvent
        this.template(
            'src/main/resources/config/liquibase/fake-data/calendar_event.csv',
            resourceDir + 'config/liquibase/fake-data/calendar_event.csv'
        );
        this.template(
            'src/main/resources/config/liquibase/changelog/20200211162500_added_entity_constraints_CalendarEvent.xml',
            resourceDir + 'config/liquibase/changelog/20200211162500_added_entity_constraints_CalendarEvent.xml'
        );
        this.template(
            'src/main/resources/config/liquibase/changelog/20200211162500_added_entity_CalendarEvent.xml',
            resourceDir + 'config/liquibase/changelog/20200211162500_added_entity_CalendarEvent.xml'
        );

        // CalendarProvider
        this.template(
            'src/main/resources/config/liquibase/fake-data/calendar_provider.csv',
            resourceDir + 'config/liquibase/fake-data/calendar_provider.csv'
        );
        this.template(
            'src/main/resources/config/liquibase/changelog/20200211162700_added_entity_constraints_CalendarProvider.xml',
            resourceDir + 'config/liquibase/changelog/20200211162700_added_entity_constraints_CalendarProvider.xml'
        );
        this.template(
            'src/main/resources/config/liquibase/changelog/20200211162700_added_entity_CalendarProvider.xml',
            resourceDir + 'config/liquibase/changelog/20200211162700_added_entity_CalendarProvider.xml'
        );

        // Changelog added
        this.addLiquibaseChangelogToMaster('20200211162600_added_entity_Calendar', needle_liquibase);
        this.addLiquibaseChangelogToMaster('20200211162500_added_entity_CalendarEvent', needle_liquibase);
        this.addLiquibaseChangelogToMaster('20200211162700_added_entity_CalendarProvider', needle_liquibase);
        this.addLiquibaseChangelogToMaster('20200211162600_added_entity_constraints_Calendar', needle_liquibase_constraints);
        this.addLiquibaseChangelogToMaster('20200211162500_added_entity_constraints_CalendarEvent', needle_liquibase_constraints);
        this.addLiquibaseChangelogToMaster('20200211162700_added_entity_constraints_CalendarProvider', needle_liquibase_constraints);
    }

    install() {
        const logMsg = `To install your dependencies manually, run: ${chalk.yellow.bold(`${this.clientPackageManager} install`)}`;

        const injectDependenciesAndConstants = err => {
            if (err) {
                this.warning('Install of dependencies failed!');
                this.log(logMsg);
            }
        };
        const installConfig = {
            bower: false,
            npm: this.clientPackageManager !== 'yarn',
            yarn: this.clientPackageManager === 'yarn',
            callback: injectDependenciesAndConstants
        };
        if (this.options['skip-install']) {
            this.log(logMsg);
        } else {
            this.installDependencies(installConfig);
        }
    }

    end() {
        this.log('End of fullcalendar generator');
    }
};

//Attaching our method to the String Object
String.prototype.cleanup = function() {
    const re = /([^a-zA-Z0-9][a-zA-Z])/g;
    var tamp = this.replace(re, function(x) {
        return x.toUpperCase();
    }).replace(/[^a-zA-Z0-9]/g, '');
    return tamp.charAt(0).toUpperCase() + tamp.slice(1);
};

function execute(command) {
    const exec = require('child_process').exec;

    exec(command, (err, stdout, stderr) => {
        process.stdout.write(stdout);
    });
}
