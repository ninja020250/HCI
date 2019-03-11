namespace API.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class imageurl : DbMigration
    {
        public override void Up()
        {
            DropColumn("dbo.Animals", "ImageUrl");
        }
        
        public override void Down()
        {
            AddColumn("dbo.Animals", "ImageUrl", c => c.String());
        }
    }
}
