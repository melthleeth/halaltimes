# Generated by Django 3.1.3 on 2021-04-07 12:02

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('recommendations', '0001_initial'),
    ]

    operations = [
        migrations.AlterField(
            model_name='djangoreview',
            name='id_user',
            field=models.IntegerField(),
        ),
    ]
